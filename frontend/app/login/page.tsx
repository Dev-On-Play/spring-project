"use client"

import { useCallback, useEffect, useState } from "react"
import { NextPage } from "next"
import GoogleIcon from "@/components/icons/GoogleIcon"
import KakaoIcon from "@/components/icons/kakaoIcon"
import NaverIcon from "@/components/icons/NaverIcon"

interface Props {}
const Login: NextPage<Props> = ({}) => {
  //카카오 로그인시 리턴 인가 코드
  const [code, setCode] = useState<string | undefined | null>()
  if (
    typeof window !== "undefined" &&
    new URL(window.location.href).searchParams.get("code") &&
    !code
  ) {
    //정상적으로 인가코드 정보를 받았을때
    setCode(new URL(window.location.href).searchParams.get("code"))
  }
  // 인가 코드 받기 요청 실패
  // error 및 error_description이 전달된 경우
  // 문제 해결, 응답 코드를 참고해 에러 원인별 상황에 맞는 서비스 페이지나 안내 문구를 사용자에게 보여주도록 처리
  // => #todo

  //로그인 REST API
  const kakaoRestApi = () => {
    const redirect_uri = `${process.env.NEXT_PUBLIC_DOMAIN}/login` //Redirect URI
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api 참고 문서
    // oauth 요청 URL
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_REST_API_KEY}&redirect_uri=${redirect_uri}&response_type=code`
    //로그인 실패시 or 기존 인가코드를 가지고 있다면 code 를 초기화
    if (code) {
      setCode("")
    }
    window.location.href = kakaoURL
  }
  //실제 로그인 처리
  const kakaoLogin = useCallback(() => {
    console.log("백엔드로 인가코드 전달", code)
  }, [code])
  useEffect(() => {
    if (code) {
      kakaoLogin()
    }
  }, [code, kakaoLogin])

  const loginHandler = (trigger: string) => {
    if (trigger.includes("k")) {
      kakaoRestApi()
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
export default Login
