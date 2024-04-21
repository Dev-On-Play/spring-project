"use client"

import { useLoadingStore } from "@/store/store"
import FadeLoader from "react-spinners/FadeLoader"
import { NextPage } from "next"

interface Props {}

const CommonLoading: NextPage<Props> = ({}) => {
  const { isLoading } = useLoadingStore((state) => state)
  return (
    <>
      {isLoading ? (
        <div className="z-9999 fixed flex h-screen w-screen translate-x-0 content-center items-center justify-center justify-items-center bg-[#ffffffb7]">
          <FadeLoader color="#44CF6C" loading={isLoading} speedMultiplier={1} />
        </div>
      ) : null}
    </>
  )
}

export default CommonLoading
