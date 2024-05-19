import React from "react"
import { Button } from "@/components/ui/button"

interface CategoryButtonProps {
  category: string
  onClick: () => void
  isSelected: boolean
}

const CategoryButton: React.FC<CategoryButtonProps> = ({
  category,
  onClick,
  isSelected,
}) => {
  return (
    <Button
      variant="default"
      size="default"
      onClick={onClick}
      className={isSelected ? "bg-blue-500 text-white" : ""}
    >
      {category}
    </Button>
  )
}

export default CategoryButton
