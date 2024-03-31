import axios from "axios"

export const instance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
})
instance.interceptors.request.use(
  (config) => {
    //요청을 보내기 전에 수행할 로직
    return config
  },
  (error) => {
    //요청 에러가 발생했을 때 수행할 로직
    console.log(error)
    return Promise.reject(error)
  }
)
instance.interceptors.response.use(
  (response) => {
    //응답에 대한 로직 작성
    const res = response.data
    return res
  },

  (error) => {
    //응답 에러가 발생했을 때 수행할 로직
    console.log(error) //디버깅
    return Promise.reject(error)
  }
)
const fetchApi = async (
  apiUrl: string,
  opts: {
    method:
      | "get"
      | "GET"
      | "delete"
      | "DELETE"
      | "head"
      | "HEAD"
      | "options"
      | "OPTIONS"
      | "post"
      | "POST"
      | "put"
      | "PUT"
      | "patch"
      | "PATCH"
      | "purge"
      | "PURGE"
      | "link"
      | "LINK"
      | "unlink"
      | "UNLINK"
    data?: { [key: string]: any }
    params?: any
    headers?: any
  }
) => {
  const headers = opts.headers
    ? { ...opts.headers }
    : { "Content-Type": "application/json" }
  return instance(apiUrl, {
    method: opts.method,
    url: apiUrl,
    data: opts.data,
    headers: headers,
  })
    .then((res) => {
      return { ...res, isOk: true }
    })
    .catch((err) => {
      return { ...err, isOk: false }
    })
}
export default fetchApi
