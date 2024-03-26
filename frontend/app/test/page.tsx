"use client";

import Link from "next/link";
import CommonAccordion from "@/components/common/Accordion";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { CommonAlert } from "@/components/common/Alert";
import { useCountStore } from "@/hooks/store";

import CommonButton from "@/components/common/Button";

export default function Component() {
  const router = useRouter();
  const goLogin = () => {
    router.push("/login");
  };
  const [accordionData, setAccordionData] = useState<{
    title: string;
    contents: string;
    [key: string]: any;
  }>({
    title: "아코디언 타이틀",
    contents: "아코디언 내부 컨텐츠 ",
  });
  const alertMsg = {
    variant: "destructive",
    title: "알림 타이틀",
    contents: "알림 내용",
  };
  const { countState, plusCount, initCount } = useCountStore();
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
    </div>
  );
}
