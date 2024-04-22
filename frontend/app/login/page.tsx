"use client"

import { useEffect, useState } from "react"
import GoogleIcon from "@/components/icons/GoogleIcon"
import KakaoIcon from "@/components/icons/kakaoIcon"
import NaverIcon from "@/components/icons/NaverIcon"

export default function Login() {
  //카카오 로그인시 리턴 인가 코드
  const [code, setCode] = useState<string | undefined | null>()
  if (
    typeof window !== "undefined" &&
    new URL(window.location.href).searchParams.get("code") &&
    !code
  ) {
    setCode(new URL(window.location.href).searchParams.get("code"))
  }
  const kakaoLogin = () => {
    console.log("백엔드에 인가 코드 전달")
  }
  useEffect(() => {
    if (code) {
      kakaoLogin()
    }
  }, [code])

  const loginHandler = (trigger: string) => {
    if (trigger.includes("k")) {
      const redirect_uri = `${process.env.NEXT_PUBLIC_DOMAIN}/login` //Redirect URI
      // oauth 요청 URL
      const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_REST_API_KEY}&redirect_uri=${redirect_uri}&response_type=code`
      window.location.href = kakaoURL
    } else if (trigger.includes("n")) {
      console.log("네이버 로그인")
    } else if (trigger.includes("g")) {
      console.log("구글 로그인")
    }
  }
  return (
    <div className="container flex h-[800px] max-w-[750px] flex-col place-content-between items-center justify-center">
      <p className="text-[48px] font-bold">모각코 로그인</p>
      <div
        className="mb-[10px] rounded-2xl shadow-md hover:cursor-pointer"
        onClick={() => {
          loginHandler("k")
        }}
      >
        <KakaoIcon />
      </div>
      <div
        className="mb-[10px] rounded-2xl shadow-md hover:cursor-pointer"
        onClick={() => {
          loginHandler("n")
        }}
      >
        <NaverIcon />
      </div>
      <div
        className="mb-[10px] rounded-2xl shadow-md hover:cursor-pointer"
        onClick={() => {
          loginHandler("g")
        }}
      >
        <GoogleIcon />
      </div>
    </div>
  )
}
