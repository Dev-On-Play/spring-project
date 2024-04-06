"use client"

import React from "react"
import { Button } from "@/components/ui/button"

export default function CommonButton(props: {
  text: string
  fontSize?: string
  className?: string
  variant?:
    | "default"
    | "destructive"
    | "outline"
    | "secondary"
    | "ghost"
    | "link"
    | null
    | undefined
  onClick?: () => {}
}) {
  return (
    <Button
      className={props.className}
      variant={props.variant}
      onClick={props.onClick}
    >
      <span className={props.fontSize}>{props.text}</span>
    </Button>
  )
}
