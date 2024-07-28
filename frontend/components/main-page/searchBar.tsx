import React, { useState } from "react"
import { Input } from "@/components/ui/input"

// Define props type for the component
interface Event {
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
}

interface SearchBarProps {
  list: Event[]
  onSearchChange: (keyword: string) => void
}

const SearchBarMain: React.FC<SearchBarProps> = ({ list, onSearchChange }) => {
  const [keyword, setKeyword] = useState("")

  // 검색어 입력 시
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newKeyword = event.target.value
    setKeyword(newKeyword)
    onSearchChange(newKeyword)
  }

  return (
    <div className="w-full mt-4">
      {/* 검색창 */}
      <Input
        type="text"
        placeholder="Search..."
        value={keyword}
        onChange={handleInputChange}
      />
    </div>
  )
}

export default SearchBarMain
