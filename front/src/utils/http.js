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
    if (isJson) {
      data = await res.json().catch(() => null)
    } else {
      data = await res.text().catch(() => '')
    }

    //todo ok??和BusinessException？？待测试与修改
    if (!res.ok) {
      // HTTP 状态码错误（500 等）
      const msg = data?.message || (typeof data === 'string' && data ? data : `HTTP ${res.status}`)
      const err = new Error(msg)
      err.status = res.status
      err.body = data
      throw err
    }

    // 检查系统内部的错误（ok: false && BusinessException: false）
    if (data && typeof data === 'object' && data.ok === false && data.BusinessException === false) {
      const err = new Error(data.message || '系统错误')
      err.status = res.status
      err.body = data
      throw err
    }

    // 检查业务层面的错误（ok: false && BusinessException: true）
    if (data && typeof data === 'object' && data.ok === false && data.BusinessException === true) {
      const err = new Error(data.message || '业务错误')
      err.status = res.status
      err.body = data
      err.isBusinessError = true
      throw err
    }

    if (options.responseType === 'text' || !isJson) {
      return data
    }
    return data
  } catch (e) {
    if (e?.name === 'AbortError') {
      const err = new Error('timeout')
      err.code = 'TIMEOUT'
      throw err
    }
    // 重新抛出错误，让调用方处理
    throw e
  } finally {
    clearTimeout(timer)
  }
}

export const http = {
  getText(path, opts = {}) {
    return request(path, { ...opts, method: 'GET', responseType: 'text' })
  },
  get(path, opts = {}) {
    return request(path, { ...opts, method: 'GET' })
  },
  postJson(path, data, opts = {}) {
    return request(path, {
      ...opts,
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...(opts.headers || {}) },
      body: JSON.stringify(data ?? {}),
    })
  },
  del(path, opts = {}) {
    return request(path, { ...opts, method: 'DELETE' })
  },
}

