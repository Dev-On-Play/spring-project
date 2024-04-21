"use client"

import GoogleIcon from "@/components/icons/GoogleIcon"
import KakaoIcon from "@/components/icons/kakaoIcon"
import NaverIcon from "@/components/icons/NaverIcon"

export default function Login() {
  const loginHandler = (trigger: string) => {
    if (trigger.includes("k")) {
      console.log("카카오 로그인")
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
