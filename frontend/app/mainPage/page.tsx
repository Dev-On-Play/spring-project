"use client"

import { useEffect, useState } from "react"
import { Button } from "@/components/ui/button"
import CategoryButton from "@/components/main-page/categoryButton"
import SearchBarMain from "@/components/main-page/searchBar"
import MogakoInfoCard from "../../components/main-page/mogakoCard"
import cardViewData from "./exampleDatas/cardViewExData.json"

export default function MainPage() {
  // 모각코 정보를 각 카드에 담아서 보여주기 위한 state
  const [cards, setCards] = useState<
    {
      profileImageUrl: string // 프로필 이미지 URL
      nickname: string // 작성 유저 닉네임
      title: string // 모각코 제목
      date: string // 모임 날짜
      category: string
      minPeople: number // 최소 인원
      currentPeople: number // 현재 참여 인원
      maxPeople: number // 최대 인원
      hashtags: string[]
      content: string
    }[]
  >([])

  const [selectedCategory, setSelectedCategory] = useState<string | null>(null) // 카테고리 선택 | 미선택 (null)
  const [searchKeyword, setSearchKeyword] = useState<string>("") // 검색어

  // Rest API would be called here
  // await fetch("../api/mogakos") 수정

  // useEffect(() => {
  //   async function fetchCards() {
  //     try {
  //       const response = await fetch("../api/mogakos")
  //       const data = await response.json()
  //       setCards(data)
  //     } catch (error) {
  //       console.error("Error fetching card data:", error)
  //     }
  //   }

  //   fetchCards()
  // }, [])

  useEffect(() => {
    // json 데이터를 이용하여 카드 데이터 초기화
    setCards(cardViewData)
  }, [])

  // 카테고리를 중복 없이 cardViewData 에서 추출
  const uniqueCategories = Array.from(
    new Set(cardViewData.map((card) => card.category))
  )

  // 카테고리 버튼 클릭 시 동작
  const handleCategoryClick = (category: string) => {
    setSelectedCategory((prevCategory) =>
      prevCategory === category ? null : category
    )
  }

  // 검색어 변경 시 동작
  const handleSearchChange = (keyword: string) => {
    setSearchKeyword(keyword)
  }

  // 카테고리와 검색어에 따라 카드 필터링
  const filteredCards = cards.filter((card) => {
    // 카테고리 선택 여부
    const matchesCategory = selectedCategory
      ? card.category === selectedCategory
      : true
    // 검색어 일치 여부
    const matchesKeyword = searchKeyword.startsWith("#")
      ? searchKeyword
          .split(" ")
          .some((tag) =>
            card.hashtags
              .map((h) => h.toLowerCase())
              .includes(tag.slice(1).toLowerCase())
          )
      : card.title.toLowerCase().includes(searchKeyword.toLowerCase())
    // 두 조건을 모두 적용한 게시물 반환
    return matchesCategory && matchesKeyword
  })
  return (
    <>
      {/* Declare a page title */}
      <title>MOS - Main Page</title>
      {/* Main Page Part Starts*/}

      {/* Search Bar */}
      <div className="max-w-2xl w-full">
        <SearchBarMain list={cards} onSearchChange={handleSearchChange} />
      </div>

      {/* Category Buttons */}
      <div className="flex flex-wrap w-full gap-3 max-w-xl items-start justify-between my-5">
        {uniqueCategories.map((category) => (
          <CategoryButton
            key={category}
            category={category}
            onClick={() => handleCategoryClick(category)}
            isSelected={selectedCategory === category}
          />
        ))}
      </div>

      {/* Cards Container */}
      <div className="flex gap-3 w-full max-w-6xl my-5">
        <div className="container mx-auto p-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {/* 카드 데이터를 이용하여 카드 렌더링 */}
            {filteredCards.map((card, index) => (
              <MogakoInfoCard key={index} {...card} />
            ))}
          </div>
        </div>
      </div>
    </>
  )
}