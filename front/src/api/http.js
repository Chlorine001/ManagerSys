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
  const url = joinUrl(base, path)

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

    if (!res.ok) {
      // HTTP 状态码错误（500 等）
      const msg = data?.message || (typeof data === 'string' && data ? data : `HTTP ${res.status}`)
      const err = new Error(msg)
      err.status = res.status
      err.body = data
      throw err
    }

    // 检查业务层面的错误（ok: false）
    if (data && typeof data === 'object' && data.ok === false) {
      const err = new Error(data.message || '请求失败')
      err.status = res.status
      err.body = data
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

