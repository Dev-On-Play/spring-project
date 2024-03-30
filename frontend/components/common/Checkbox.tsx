"use client"

import { useEffect, useState } from "react"
import { NextPage } from "next"
import { Checkbox } from "@/components/ui/checkbox"

interface Props {
  isChecked?: boolean
  onCheckedChange?: any
  disabled?: boolean
  value?: string
  text?: string
  index?: number
}

const CommonCheckbox: NextPage<Props> = ({
  isChecked = false,
  onCheckedChange,
  disabled = false,
  value = "",
  text = "",
  index = 0,
}) => {
  const [checked, setChecked] = useState<any>(isChecked)
  const onChange = (e: any) => {
    setChecked(e)
    onCheckedChange(e, index)
  }
  return (
    <div className="flex items-center space-x-2">
      <Checkbox
        id="terms"
        checked={checked}
        onCheckedChange={onChange}
        disabled={disabled}
        value={value}
      />
      <label
        htmlFor="terms"
        className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
      >
        {text}
      </label>
    </div>
  )
}

export default CommonCheckbox
