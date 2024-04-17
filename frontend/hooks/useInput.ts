"use client"

import { useCallback, useState } from "react"

const useInput = (initialValue: any) => {
  const [value, setValue] = useState(initialValue)
  const handler = useCallback((e: any) => {
    setValue(e.target.value)
  }, [])
  return [value, handler, setValue]
}

export { useInput }
