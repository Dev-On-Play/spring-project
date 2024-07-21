"use client"

import fetchApi from "@/api/fetchApi"
import { getItem, setItem } from "@/store/localStorage"
import { useLoadingStore } from "@/store/store"
import { useCallback, useEffect, useState } from "react"
import { NextPage } from "next"
import { useRouter } from "next/navigation"
import GoogleIcon from "@/components/icons/GoogleIcon"
import KakaoIcon from "@/components/icons/kakaoIcon"
import NaverIcon from "@/components/icons/NaverIcon"

interface Props {}
const Login: NextPage<Props> = ({}) => {
  const router = useRouter()
  const { isLoading, offLoading, onLoading } = useLoadingStore()
  //로그인시 리턴 인가 코드
  const [code, setCode] = useState<string | undefined | null>("")
  useEffect(() => {
    const accessToken = getItem({ key: "accessToken" })
    // logout()
    if (accessToken) {
      //엑세스 토큰 유효성 검증 로직 필요함
      console.log(accessToken)
    }
    if (
      typeof window !== "undefined" &&
      new URL(window.location.href).searchParams.get("code") &&
      !code
    ) {
      //정상적으로 인가코드 정보를 받았을때
      setCode(new URL(window.location.href).searchParams.get("code"))
    }
  }, [])

  // 인가 코드 받기 요청 실패
  // error 및 error_description이 전달된 경우
  // 문제 해결, 응답 코드를 참고해 에러 원인별 상황에 맞는 서비스 페이지나 안내 문구를 사용자에게 보여주도록 처리
  // => #todo
  // 실제 로그인 처리
  const loginProcessing = useCallback(async () => {
    onLoading()
    await fetchApi("/auth/login", {
      method: "post",
      data: {
        oauthProvider: getItem({ key: "loginType" }),
        code: code,
      },
    })
      .then((res) => {
        setItem({ key: "accessToken", item: res.data.accessToken })
        offLoading()
        const lastPath = getItem({ key: "lastPath" })
        lastPath ? router.push(lastPath) : router.push("mainPage")
      })
      .catch((err) => {
        offLoading()
      })
  }, [code])
  useEffect(() => {
    if (code) {
      loginProcessing()
    }
  }, [code, loginProcessing])
  const loginHandler = (trigger: string) => {
    setItem({ key: "loginType", item: trigger })
    const redirect_uri = `${process.env.NEXT_PUBLIC_DOMAIN}/login` //Redirect URI
    let loginURL = ""
    if (trigger.includes("k")) {
      loginURL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_REST_API_KEY}&redirect_uri=${redirect_uri}&response_type=code`
    }
    if (trigger.includes("n")) {
      loginURL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${process.env.NEXT_PUBLIC_NAVER_CLIENT_ID}&redirect_uri=${redirect_uri}`
    }
    if (trigger.includes("g")) {
      loginURL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.NEXT_PUBLIC_GOOGLE_CLIENT_ID}&redirect_uri=${redirect_uri}&response_type=code&scope=email profile`
    }
    if (code) {
      setCode("")
    }
    window.location.href = loginURL
  }

  return (
    <div suppressHydrationWarning>
      <div className="container flex h-[800px] max-w-[750px] flex-col place-content-between items-center justify-center">
        <p className="text-[48px] font-bold">모각코 로그인</p>
        <div
          className="mb-[10px] rounded-2xl shadow-md hover:cursor-pointer"
          onClick={() => {
            loginHandler("kakao")
          }}
        >
          <KakaoIcon />
        </div>
        <div
          className="mb-[10px] rounded-2xl shadow-md hover:cursor-pointer"
          onClick={() => {
            loginHandler("naver")
          }}
        >
          <NaverIcon />
        </div>
        <div
          className="mb-[10px] rounded-2xl shadow-md hover:cursor-pointer"
          onClick={() => {
            loginHandler("google")
          }}
        >
          <GoogleIcon />
        </div>
      </div>
    </div>
  )
}
export default Login
