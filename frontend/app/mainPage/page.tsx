"use client"

import { useEffect, useState } from "react"
import { Button } from "@/components/ui/button"
import MogakoInfoCard from "./components/mogakoCard"
import cardViewData from "./exampleDatas/cardViewExData.json"

export default function MainPage() {
  const [cards, setCards] = useState<
    {
      profileImageUrl: string
      nickname: string
      title: string
      date: string
      category: string
      minPeople: number
      currentPeople: number
      maxPeople: number
      hashtags: string[]
      content: string
    }[]
  >([])

  useEffect(() => {
    // Load the data into state
    setCards(cardViewData)
  }, [])

  return (
    <>
      {/* Declare a page title */}
      <title>MOS - Main Page</title>
      {/* Main Page Part Starts*/}
      {/* <SearchFieldForMainPage /> */}
      {/* Category Selector */}

      {/* Category Buttons -> 추후에는 json 에 카테고리를 추가 + for*/}
      <div className="flex flex-wrap w-full gap-3 max-w-xl items-start justify-between my-5">
        <Button>모두 표시</Button>
        <Button>AI</Button>
        <Button>VR</Button>
        <Button>백엔드</Button>
        <Button>프론트엔드</Button>
        <Button>웹</Button>
        <Button>뭐든지</Button>
        <Button>안드로이드</Button>
        <Button>iOS</Button>
        <Button>게임</Button>
        <Button>데이터베이스</Button>
        <Button>클라우드</Button>
        <Button>빅데이터</Button>
        <Button>보안</Button>
        <Button>블록체인</Button>
      </div>
      {/* Card Container */}
      <div className="flex gap-3 w-full max-w-6xl my-5">
        <div className="container mx-auto p-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {cards.map((card, index) => (
              <MogakoInfoCard key={index} {...card} />
            ))}
          </div>
        </div>
      </div>
    </>
  )
}
