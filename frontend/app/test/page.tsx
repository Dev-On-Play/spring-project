"use client"

import { useCountStore } from "@/store/store"
import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import CommonAccordion from "@/components/common/Accordion"
import { CommonAlert } from "@/components/common/Alert"
import CommonAlertDialog from "@/components/common/AlertDialog"
import CommonAvatar from "@/components/common/Avatar"
import CommonBadge from "@/components/common/Badge"
import CommonBeadcrumb from "@/components/common/Beadcrumb"
import CommonButton from "@/components/common/Button"
import CommonCalendar from "@/components/common/Calendar"
import CommonCheckbox from "@/components/common/Checkbox"

export default function Component() {
  const router = useRouter()

  const [accordionData, setAccordionData] = useState<{
    title: string
    contents: string
    [key: string]: any
  }>({
    title: "아코디언 타이틀",
    contents: "아코디언 내부 컨텐츠 ",
  })
  const alertMsg = useState<any>({
    variant: "destructive",
    title: "알림 타이틀",
    contents: "알림 내용",
  })
  const { countState, plusCount, initCount } = useCountStore()
  const badge: any[] = ["outline", "secondary", "destructive", "default"]
  const breadcrumb: { text: string; routerUrl?: string }[] = [
    { text: "Home" },
    { text: "Component" },
    { text: "MyPage", routerUrl: "/test" },
  ]

  const [calendarValue, setCalendarValue] = useState<Date | undefined>(
    new Date()
  )
  const [checkList, setCheckList] = useState<
    {
      isChecked: boolean
      value: string
      text: string
    }[]
  >([
    { isChecked: false, value: "텍스트1", text: "텍스트1" },
    { isChecked: false, value: "텍스트2", text: "텍스트2" },
    { isChecked: false, value: "텍스트3", text: "텍스트3" },
  ])
  const chanageCheck = (ch: boolean, checkdIdx: number) => {
    const tmp = [...checkList]
    tmp[checkdIdx].isChecked = ch
    setCheckList(tmp)
  }
  return (
    <div>
      <CommonBeadcrumb items={breadcrumb} />
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
      />
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
          return (
            <CommonBadge
              key={idx}
              variant={item}
              text={item}
              isLink={true}
              linkUrl={"/"}
            />
          )
        }
      )}
      <CommonCalendar date={calendarValue} changeDate={setCalendarValue} />

      {checkList.map((item, idx) => {
        return (
          <CommonCheckbox
            key={idx}
            index={idx}
            isChecked={item.isChecked}
            value={item.value}
            text={item.text}
            onCheckedChange={chanageCheck}
          />
        )
      })}
    </div>
  )
}
