"use client"

import { NextPage } from "next"
import Link from "next/link"
import { Badge, badgeVariants } from "@/components/ui/badge"

interface Props {
  variant?: "destructive" | "outline" | "secondary" | "default"
  text: string
  isLink?: boolean
  linkUrl?: string
}

const CommonBadge: NextPage<Props> = ({
  variant = "outline",
  text,
  isLink = false,
  linkUrl = "#",
}) => {
  return (
    <>
      {isLink ? (
        <Link
          href={linkUrl}
          className={badgeVariants({ variant: `${variant}` })}
        >
          {text}
        </Link>
      ) : (
        <Badge variant={variant}>{text}</Badge>
      )}
    </>
  )
}

export default CommonBadge
