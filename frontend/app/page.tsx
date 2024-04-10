"use client";
import { usePathname, useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Home() {
  const router = useRouter();
  const path = usePathname();
  //권한에 따른 > 관리자 페이지 or일반 사용자 페이지 이동 관리
  useEffect(() => {
    if (path === "/") {
      router.push("/mainPage");
    }
  }, [path,router]);

  return <></>;
}
