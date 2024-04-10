"use client"

import fetchApi from "@/api/fetchApi"
import { useEffect } from "react"
import { NextPage } from "next"
// import { useRouter } from "next/router"
import { useParams, usePathname, useRouter } from "next/navigation"
import { useInput } from "@/hooks/useInput"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import CardComments from "@/components/card/Comments"
import CommonButton from "@/components/common/Button"

interface Props {}

const DetailPage: NextPage<Props> = ({}) => {
  const coments = Array.from({ length: 30 }, () => 0).map((item, idx) => {
    return {
      contents: `댓글 영역입니다 ${idx + 1}`,
      profile: "https://github.com/shadcn.png",
      nickName: `닉네임${idx + 1}`,
      createDate: `2024-04-${idx + 1} 00:00:00`,
    }
  })
  const [{ inputVal }, onInputChange] = useInput({ inputVal: "" })
  const path = useRouter()
  const params = useParams()
  const getDetail = async () => {
    if (params.id) {
      const { data, error } = await fetchApi(`/mogakos/${params.id}`, {
        method: "get",
      })
      console.log(error)
      console.log(data)
      // 통신 성공시 error undefined
      if (!error) {
        //통신 성공후 처리 로직
      } else {
        //통신 실패후 처리 로직
      }
    }
  }
  useEffect(() => {
    getDetail()
  })
  return (
    <>
      <div className="container max-w-[750px]">
        <CommonButton
          text={"뒤로"}
          fontSize={"text-[12px]"}
          className={
            "start-0 h-[30px] w-[67px] rounded-2xl bg-[#32a287] hover:bg-themeColor5"
          }
          variant={"default"}
        />
      </div>
      <div className="container flex h-[58px] max-w-[750px] place-content-between items-center">
        <div className="w-3/5 text-[26px] text-black">타이틀 영역</div>
        <div>
          <CommonButton
            text={"참여"}
            className={
              "start-0 h-[30px] w-[67px] rounded-2xl bg-[#32a287] hover:bg-themeColor5"
            }
            fontSize={"text-[12px]"}
            variant={"default"}
          />
        </div>
      </div>
      <div className="container flex h-[58px] max-w-[750px] place-content-between items-center">
        <div className="flex w-3/5">
          <div className="h-[42px]  w-[97px] content-center rounded-2xl border border-[#a1a1aa] text-center">
            <span className="text-[12px]">카테고리</span>
          </div>
          <div className="flex h-[42px] min-w-[150px] flex-wrap content-center items-center justify-center rounded-2xl border border-[#a1a1aa] text-center text-[12px]">
            <div>
              <span className="">최소 : </span>
              <span>N 명 </span>
            </div>
            <div className="ml-2">
              <span>현재 : </span>
              <span>1 / N</span>
            </div>
          </div>
        </div>
        <div className="h-[42px] w-[234px] content-center rounded-2xl border border-solid border-[#a1a1aa80] bg-[#efefef99] text-center">
          <span className="text-[12px] leading-[100%]">날짜 정보</span>
        </div>
      </div>
      <div className="container h-[58px] max-w-[750px]">
        <ul className="flex gap-4 text-[12px] text-themeColor4">
          <li>#해시태그1</li>
          <li>#해시태그1</li>
          <li>#해시태그1</li>
          <li>#해시태그1</li>
          <li>#해시태그1</li>
        </ul>
      </div>
      <div className="container max-w-[750px] ">
        <Textarea
          className="min-h-[326px] resize-none bg-[#efefef99]"
          readOnly={true}
          value={"텍스트 영역"}
        ></Textarea>
      </div>
      <div className="container max-w-[750px]">
        <br />
        <div>
          <Input
            className="h-[56px]"
            type="string"
            value={inputVal}
            placeholder="댓글을 입력해주세요"
            onChange={onInputChange}
          />
        </div>
        <br />
        {coments.map((item, idx) => {
          return (
            <CardComments
              key={idx}
              contents={item.contents}
              profile={item.profile}
              nickName={item.nickName}
              createDate={item.createDate}
              childComment={idx % 2 === 0 ? [coments[idx]] : []}
            />
          )
        })}
      </div>
    </>
  )
}

export default DetailPage
