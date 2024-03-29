"use client"

import { useCountStore } from "@/store/store"
import { useState } from "react"
import Link from "next/link"
import { useRouter } from "next/navigation"
import CommonAccordion from "@/components/common/Accordion"
import { CommonAlert } from "@/components/common/Alert"
import CommonAlertDialog from "@/components/common/AlertDialog"
import CommonAvatar from "@/components/common/Avatar"
import CommonBadge from "@/components/common/Badge"
import CommonButton from "@/components/common/Button"

export default function Component() {
  const router = useRouter()
  const goLogin = () => {
    router.push("/login")
  }
  const [accordionData, setAccordionData] = useState<{
    title: string
    contents: string
    [key: string]: any
  }>({
    title: "아코디언 타이틀",
    contents: "아코디언 내부 컨텐츠 ",
  })
  const alertMsg = {
    variant: "destructive",
    title: "알림 타이틀",
    contents: "알림 내용",
  }
  const { countState, plusCount, initCount } = useCountStore()
  const badge: any[] = ["outline", "secondary", "destructive", "default"]
  return (
    <div>
      <CommonAccordion props={accordionData}></CommonAccordion>
      <div style={{ width: "400px" }}>
        <CommonAlert props={alertMsg} />
      </div>

      <h1>zustand 상태관리 </h1>
      <div>{countState.count}</div>
      <CommonButton
        text={"증가"}
        className={""}
        variant={"default"}
        onClick={plusCount}
      ></CommonButton>
      <br />
      <CommonButton
        text={"초기화"}
        className={""}
        variant={"default"}
        onClick={initCount}
      ></CommonButton>
      <br />
      <CommonAlertDialog
        callText={"호출 버튼 텍스트"}
        dialogTitle={"타이틀"}
        dialogText={"내부 텍스트"}
        isCancell={true}
        cancellText={"취소"}
        confirmText={"확인"}
      />
      <br />
      <CommonAvatar imgSrc={"https://github.com/shadcn.png"} />
      <br />
      {badge.map(
        (item: "destructive" | "outline" | "secondary" | "default", idx) => {
          return <CommonBadge variant={item} text={item} />
        }
      )}
    </div>
  )
}
