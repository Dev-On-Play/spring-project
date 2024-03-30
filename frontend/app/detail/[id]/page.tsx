"use client"

import { usePathname, useRouter, useSearchParams } from "next/navigation"

export default function Detail() {
  const router = useRouter()
  const pathname = usePathname()
  return <>상세페이지 params = {pathname}</>
}
