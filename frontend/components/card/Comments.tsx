"use client"

import { useCallback, useState } from "react"
import { NextPage } from "next"
import { useInput } from "@/hooks/useInput"
import CommonAvatar from "@/components/common/Avatar"
import CommonButton from "@/components/common/Button"
import ArrowIcon from "../icons/ArrowIcon"
import { Input } from "../ui/input"

interface CommentType {
  contents: string
  profile?: string
  nickName?: string
  createDate?: string | Date
}
interface Props extends CommentType {
  className?: string
  childComment?: CommentType[]
  comentId?: string
}

const CardComments: NextPage<Props> = ({
  contents,
  profile = "https://github.com/shadcn.png",
  nickName,
  createDate,
  childComment,
  className,
}) => {
  const [inputVal, onChangeInputVal, setInputVal] = useInput("")
  const [isComment, setIsComment] = useState(false)
  const commentCardHandler = (trigger: string) => {
    console.log("trigger", trigger.includes("add"))
    if (trigger.includes("add")) {
      setIsComment(true)
    } else if (trigger.includes("cancell")) {
      setIsComment(false)
      setInputVal("")
    } else {
      // 저장버튼누를때 저장로직 동작 후 새댓글 받아오는 기능 구현
      setIsComment(false)
      setInputVal("")
    }
  }
  return (
    <div className={`${className ? className : ""} py-2`}>
      <div className="relative flex min-h-[80px] items-center rounded-2xl border border-solid border-[#a1a1aa80] shadow-md">
        <div className="ml-2 mr-4 flex flex-col items-center justify-center">
          <CommonAvatar imgSrc={profile} alt={nickName} fallback={nickName} />
          <div>{nickName}</div>
        </div>
        <div className="grow">{contents}</div>
        {/* {childComment?.length ? <div>버튼</div> : <></>} */}
        <div className="absolute bottom-0 right-2">
          {createDate?.toString()}
        </div>
        {/* 댓글이 달려있지 않을 경우에만  */}
        {(childComment && childComment?.length > 0) ||
        className?.includes("childComment") ? (
          <></>
        ) : (
          <CommonButton
            text={"댓글"}
            className={
              "mr-4 h-[30px] w-[67px] rounded-2xl bg-[#32a287] hover:bg-themeColor5"
            }
            onClick={() => commentCardHandler("add")}
            fontSize={"text-[12px]"}
            variant={"default"}
          />
        )}
      </div>
      {/* 댓글 버튼 클릭시 */}
      {isComment ? (
        <div className="relative">
          <div className="ml-12 py-2">
            <ArrowIcon />
            <div className="relative flex min-h-[80px] items-center rounded-2xl border border-solid border-[#a1a1aa80] shadow-md">
              <div className="ml-2 mr-4 flex flex-col items-center justify-center">
                <CommonAvatar
                  imgSrc={profile}
                  alt={nickName}
                  fallback={nickName}
                />
                <div>댓글 닉네임</div>
              </div>
              <div className="grow">
                {" "}
                <Input
                  className="h-[56px]"
                  type="string"
                  value={inputVal}
                  placeholder="댓글을 입력해주세요"
                  onChange={onChangeInputVal}
                />
              </div>
              <CommonButton
                text={"저장"}
                className={
                  "mx-2 h-[30px] w-[37px] rounded-2xl bg-[#32a287] hover:bg-themeColor5"
                }
                onClick={() => commentCardHandler("save")}
                fontSize={"text-[12px]"}
                variant={"default"}
              />
              <CommonButton
                text={"취소"}
                className={
                  "mr-2 h-[30px] w-[37px] rounded-2xl bg-[#32a287] hover:bg-themeColor5"
                }
                onClick={() => commentCardHandler("cancell")}
                fontSize={"text-[12px]"}
                variant={"default"}
              />
            </div>
          </div>
        </div>
      ) : null}
      {/* 댓글이 달려있지 않을 경우에 버튼 노출 */}
      {childComment && childComment.length > 0 ? (
        childComment.map((item, idx) => {
          return (
            <div className="relative" key={idx}>
              <ArrowIcon />
              <CardComments
                className="childComment ml-12"
                contents={"대댓글"}
                profile={item.profile}
                nickName={item.nickName}
                createDate={item.createDate}
              />
            </div>
          )
        })
      ) : (
        <></>
      )}
    </div>
  )
}

export default CardComments
