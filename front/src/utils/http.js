const DEFAULT_TIMEOUT_MS = 8000

function joinUrl(base, path) {
  if (!base) return path
  if (!path) return base
  const b = base.endsWith('/') ? base.slice(0, -1) : base
  const p = path.startsWith('/') ? path : `/${path}`
  return `${b}${p}`
}

async function request(path, options = {}) {
  const base = import.meta.env.VITE_API_BASE || '/api'
  
  // 处理查询参数
  let url = joinUrl(base, path)
  if (options.params) {
    const queryString = Object.entries(options.params)
      .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
      .join('&')
    url += url.includes('?') ? `&${queryString}` : `?${queryString}`
  }

  const controller = new AbortController()
  const timeoutMs = Number.isFinite(options.timeoutMs) ? options.timeoutMs : DEFAULT_TIMEOUT_MS
  const timer = setTimeout(() => controller.abort(), timeoutMs)

  try {
    const res = await fetch(url, {
      method: options.method || 'GET',
      headers: {
        ...(options.headers || {}),
      },
      body: options.body,
      signal: controller.signal,
      credentials: options.credentials || 'include',
    })

    const contentType = res.headers.get('content-type') || ''
    const isJson = contentType.includes('application/json')

    // 读取响应体
    let data
    if (options.responseType === 'text') {
      // 用户明确要文本，无论 content-type 是什么，都按文本读
      data = await res.text().catch(() => '')
    } else if (isJson) {
      data = await res.json().catch(() => null)
    } else {
      data = await res.text().catch(() => '')
    }
    console.log(data)
    
    // ① HTTP 状态码错误（404 / 500 等，后端未被 GlobalExceptionHandler 捕获时）
    if (!res.ok) {
      const msg = data?.msg || (typeof data === 'string' && data ? data : `HTTP ${res.status}`)
      const err = new Error(msg)
      err.status = res.status
      err.body = data
      err.type = 'http'
      throw err
    }

    // ② 业务层统一包装：ok === false
    if (data && typeof data === 'object' && data.ok === false) {
      //检查系统内部的错误 or 业务层面的错误
      const isBusiness = data.businessException === true
      const err = new Error(data.msg || (isBusiness ? '业务错误' : '系统错误'))
      err.status = res.status
      err.body = data
      err.isBusinessError = isBusiness
      err.type = isBusiness ? 'business' : 'system'
      err.code = data.code

      if (!isBusiness) {
        // 系统异常：后端带 error / stacktrace
        err.errorType = data.error
        err.stacktrace = data.stacktrace
      }
      throw err
    }
    return data
  } catch (e) {
    if (e?.name === 'AbortError') {
      const err = new Error('请求超时，请稍后重试')
      err.code = 'TIMEOUT'
      err.type = 'timeout'
      throw err
    }
    // fetch 网络层错误（断网、跨域被拒等）
    if (e instanceof TypeError && !e.type) {
      const err = new Error('网络异常，请检查网络连接')
      err.type = 'network'
      throw err
    }
    // 其余（上面已经构造好的 Error）直接抛出
    throw e
  } finally {
    clearTimeout(timer)
  }
}
function jsonRequest(method, path, data, opts = {}) {
  return request(path, {
    ...opts,
    method,
    headers: { 'Content-Type': 'application/json', ...(opts.headers || {}) },
    body: JSON.stringify(data ?? {}),
  })
}

export const http = {
  getText(path, opts = {}) {
    return request(path, { ...opts, method: 'GET', responseType: 'text' })
  },
  get(path, opts = {}) {
    return request(path, { ...opts, method: 'GET' })
  },
  post(path, data, opts = {}) {
    return jsonRequest('POST', path, data, opts)
  },
  put(path, data, opts = {}) {
    return jsonRequest('PUT', path, data, opts)
  },
  patch(path, data, opts = {}) {
    return jsonRequest('PATCH', path, data, opts)
  },
  delete(path, opts = {}) {
    return request(path, { ...opts, method: 'DELETE' })
  },
  // del(path, opts = {}) {
  //   const { body, ...rest } = opts
  //   return request(path, {
  //     ...rest,
  //     method: 'DELETE',
  //     headers: {
  //       ...(body ? { 'Content-Type': 'application/json' } : {}),
  //       ...(opts.headers || {}),
  //     },
  //     body: body ? JSON.stringify(body) : undefined,
  //   })
  // },
}

