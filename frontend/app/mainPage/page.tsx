"use client"

import { useEffect, useState } from "react"
import { Button } from "@/components/ui/button"
import CategoryButton from "@/components/main-page/categoryButton"
import MogakoInfoCard from "../../components/main-page/mogakoCard"
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

  const [selectedCategory, setSelectedCategory] = useState<string | null>(null)

  // Rest API would be called here by axios

  useEffect(() => {
    // Load the data into state
    setCards(cardViewData)
  }, [])

  // Set category array for category buttons.
  const uniqueCategories = Array.from(
    new Set(cardViewData.map((card) => card.category))
  )

  const handleCategoryClick = (category: string) => {
    setSelectedCategory((prevCategory) =>
      prevCategory === category ? null : category
    )
  }

  const filteredCards = selectedCategory
    ? cards.filter((card) => card.category === selectedCategory)
    : cards

  return (
    <>
      {/* Declare a page title */}
      <title>MOS - Main Page</title>
      {/* Main Page Part Starts*/}
      {/* <SearchFieldForMainPage /> */}
      {/* Category Selector */}
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
      {/* Card Container */}
      <div className="flex gap-3 w-full max-w-6xl my-5">
        <div className="container mx-auto p-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {filteredCards.map((card, index) => (
              <MogakoInfoCard key={index} {...card} />
            ))}
          </div>
        </div>
      </div>
      +
    </>
  )
}
