"use client"

import { getItem, setItem } from "@/store/localStorage"
import { useRouter } from "next/navigation"

export default function Header() {
  const router = useRouter()
  const goPage = () => {
    // 로그인 유무 체크 후 마이페이지 또는 로그인 페이지로 이동
    const accessToken = getItem({ key: "accessToken" })
    //라스트 패스 저장
    !accessToken ? setItem({ key: "lastPath", item: "/mypage" }) : undefined
    accessToken ? router.push("/mypage") : router.push("/login")
  }
  return (
    <div className="flex flex-row justify-around pt-6">
      <div className="grid w-[92px] place-items-center pr-8"></div>
      <div className="box-border grid h-24 w-80 place-items-center overflow-hidden bg-violet-500">
        로고 영역
      </div>
      <div className="grid place-items-center pl-8">
        <svg
          className="hover:cursor-pointer"
          width="60"
          height="60"
          viewBox="0 0 60 60"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          onClick={goPage}
        >
          <g opacity="0.6">
            <circle
              cx="30"
              cy="30"
              r="29"
              fill="white"
              stroke="#3E517A"
              strokeWidth="2"
            />
            <g opacity="0.6">
              <path
                d="M29 21C29 22.5913 28.3679 24.1174 27.2426 25.2426C26.1174 26.3679 24.5913 27 23 27C21.4087 27 19.8826 26.3679 18.7574 25.2426C17.6321 24.1174 17 22.5913 17 21C17 19.4087 17.6321 17.8826 18.7574 16.7574C19.8826 15.6321 21.4087 15 23 15C24.5913 15 26.1174 15.6321 27.2426 16.7574C28.3679 17.8826 29 19.4087 29 21V21ZM45 21C45 21.7879 44.8448 22.5681 44.5433 23.2961C44.2417 24.0241 43.7998 24.6855 43.2426 25.2426C42.6855 25.7998 42.0241 26.2417 41.2961 26.5433C40.5681 26.8448 39.7879 27 39 27C38.2121 27 37.4319 26.8448 36.7039 26.5433C35.9759 26.2417 35.3145 25.7998 34.7574 25.2426C34.2002 24.6855 33.7583 24.0241 33.4567 23.2961C33.1552 22.5681 33 21.7879 33 21C33 19.4087 33.6321 17.8826 34.7574 16.7574C35.8826 15.6321 37.4087 15 39 15C40.5913 15 42.1174 15.6321 43.2426 16.7574C44.3679 17.8826 45 19.4087 45 21V21ZM36.86 43C36.952 42.346 37 41.68 37 41C37.0046 37.8577 35.9475 34.806 34 32.34C35.5202 31.4623 37.2446 31.0003 38.9999 31.0003C40.7553 31.0002 42.4797 31.4623 43.9999 32.3399C45.5201 33.2176 46.7825 34.4799 47.6602 36.0001C48.5379 37.5202 49 39.2447 49 41V43H36.86ZM23 31C25.6522 31 28.1957 32.0536 30.0711 33.9289C31.9464 35.8043 33 38.3478 33 41V43H13V41C13 38.3478 14.0536 35.8043 15.9289 33.9289C17.8043 32.0536 20.3478 31 23 31V31Z"
                fill="#3E517A"
              />
            </g>
          </g>
        </svg>
      </div>
    </div>
  )
}
