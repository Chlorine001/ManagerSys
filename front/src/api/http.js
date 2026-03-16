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

    if (!res.ok) {
      const errBody = isJson ? await res.json().catch(() => null) : await res.text().catch(() => '')
      const msg = typeof errBody === 'string' && errBody ? errBody : `HTTP ${res.status}`
      const err = new Error(msg)
      err.status = res.status
      err.body = errBody
      throw err
    }

    if (options.responseType === 'text' || !isJson) {
      return await res.text()
    }
    return await res.json()
  } catch (e) {
    if (e?.name === 'AbortError') {
      const err = new Error('timeout')
      err.code = 'TIMEOUT'
      throw err
    }
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

