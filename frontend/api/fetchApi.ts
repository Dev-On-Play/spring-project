import { getItem } from "@/store/localStorage"
import axios, { Method } from "axios"

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
    console.log(`[FetchApi] : request 오류 발생`)
    console.log(error)
    return Promise.reject(error)
  }
)
instance.interceptors.response.use(
  (response) => {
    //응답에 대한 로직 작성
    return response
  },
  (error) => {
    //응답 에러가 발생했을 때 수행할 로직
    console.log(
      `[FetchApi] : ${error.code.includes("REQUEST") ? "request" : "response"} 오류 발생`
    )
    console.log(error.response.data)
    return Promise.reject(error.response.data)
  }
)
const fetchApi = async (
  apiUrl: string,
  opts: {
    method: Method
    data?: { [key: string]: any }
    params?: any
    headers?: any
  },
  etc?: { isAuth: boolean }
) => {
  const headers = opts.headers
    ? { ...opts.headers }
    : { "Content-Type": "application/json" }
  return instance(apiUrl, {
    method: opts.method,
    url: apiUrl,
    data: opts.data,
    params: opts.params,
    headers: etc?.isAuth
      ? {
          ...headers,
          Authorization: `Bearer ${getItem({ key: "accessToken" })}`,
        }
      : headers,
  })
    .then((res) => {
      return { ...res }
    })
    .catch((err) => {
      console.log(err)
      return { ...err }
    })
}
export default fetchApi
