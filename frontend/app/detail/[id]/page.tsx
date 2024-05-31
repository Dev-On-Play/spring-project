"use client"

import fetchApi from "@/api/fetchApi"
import { useLoadingStore } from "@/store/store"
import moment from "moment"
import { useCallback, useEffect, useState } from "react"
import { NextPage } from "next"
// import { useRouter } from "next/router"
import { useParams, usePathname, useRouter } from "next/navigation"
import { useInput } from "@/hooks/useInput"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import CardComments from "@/components/card/Comments"
import CommonAlertDialog from "@/components/common/AlertDialog"
import CommonButton from "@/components/common/Button"

interface MosDetailType {
  id: number
  name: string
  summary: string
  startDate?: string
  endDate?: string
  participantLimit: number
  participantCount: number
  minimumParticipantCount: number
  detailContent: string
  [key: string]: any
}

interface Props {}
export interface MogakosComentType {
  comments: MogakosComent[]
  totalPage: number
  pageNumber: number
}

export interface MogakosComent {
  id: number
  mogako_id: number
  member: Member
  childComments: ChildMogakosComments
  contents: string
  created_date: string
}

export interface ChildMogakosComments {
  childList: ChildMogakosList[]
}

export interface ChildMogakosList {
  id: number
  mogako_id: number
  member: Member
  parents_id: number
  contents: string
  created_date: string
}

export interface Member {
  id: number
  nickname: string
  profile: string
}
const DetailPage: NextPage<Props> = ({}) => {
  const coments = Array.from({ length: 30 }, () => 0).map((item, idx) => {
    return {
      comentId: "코멘트 아이디 정보",
      contents: `댓글 영역입니다 ${idx + 1}`,
      profile: "https://github.com/shadcn.png",
      nickName: `닉네임${idx + 1}`,
      createDate: `2024-04-${idx + 1} 00:00:00`,
    }
  })
  const [inputVal, onChangeInputVal, setInputVal] = useInput("")
  const [mosDetailData, setMosDetailData] = useState<MosDetailType>()
  const path = useRouter()
  const params = useParams()
  const { onLoading, offLoading } = useLoadingStore((state) => state)
  const onParticipate = (Yn: boolean) => {
    if (Yn) {
      //참여시키기
      console.log("참여")
    } else {
      console.log("취소")
    }
  }
  const getDetail = useCallback(
    async (mogakoId?: string) => {
      if (mogakoId) {
        onLoading()
        await fetchApi(`/mogakos/${mogakoId}`, {
          method: "get",
        })
          .then((res) => {
            offLoading()
            console.log("상세조회 결과 : ", res.data)
            setMosDetailData({ ...res.data })
          })
          .catch((err) => {
            offLoading()
          })
      }
    },
    [offLoading, onLoading]
  )
  // 화면 랜더링 이후 모각코 상세 내역 조회
  useEffect(() => {
    getDetail(params.id as string)
  }, [params.id, onLoading, offLoading, getDetail])
  const addComment = () => {}
  return (
    <>
      <div className="container max-w-[750px]">
        <CommonButton
          text={"뒤로"}
          fontSize={"text-[12px]"}
          className={
            "start-0 h-[30px] w-[67px] rounded-2xl bg-[#32a287] shadow-md hover:bg-themeColor5"
          }
          variant={"default"}
        />
      </div>
      <div className="container flex h-[58px] max-w-[750px] place-content-between items-center">
        <div className="w-3/5 text-[26px] text-black">
          {mosDetailData?.name}
        </div>
        <div>
          <CommonAlertDialog
            callText={"참여"}
            dialogTitle={"모각코 참여하기"}
            dialogText={"모각코에 참여 하시겠습니까?"}
            isCancell={true}
            cancellText={"취소"}
            confirmText={"참여"}
            btnClass={
              "start-0 h-[30px] w-[67px] rounded-2xl bg-[#32a287] shadow-md hover:bg-themeColor5"
            }
            fontSize={"text-[12px]"}
            variant={"default"}
            callBack={(e) => {
              onParticipate(e)
            }}
          />
        </div>
      </div>
      <div className="container flex h-[58px] max-w-[750px] place-content-between items-center">
        <div className="flex w-3/5">
          <div className="mr-3 h-[42px] w-[97px] content-center rounded-2xl border border-[#a1a1aa] text-center shadow-md">
            <span className="text-[12px]">카테고리</span>
          </div>
          <div className="flex h-[42px] min-w-[150px] flex-wrap content-center items-center justify-center rounded-2xl border border-[#a1a1aa] text-center text-[12px] shadow-md">
            <div>
              <span className="">최소 : </span>
              <span>
                {`${mosDetailData?.minimumParticipantCount ? mosDetailData?.minimumParticipantCount : 0} 명`}{" "}
              </span>
            </div>
            <div className="ml-2">
              <span>현재 : </span>
              <span>{`${mosDetailData?.participantCount} / ${mosDetailData?.participantLimit}`}</span>
            </div>
          </div>
        </div>
        <div className="h-[42px] w-[234px] content-center rounded-2xl border border-solid border-[#a1a1aa80] bg-[#efefef99] text-center shadow-md">
          <span className="text-[12px] leading-[100%]">{`${moment(mosDetailData?.startDate).format("YYYY.MM.DD HH:mm")} ~ ${moment(mosDetailData?.endDate).format("YYYY.MM.DD HH:mm")}`}</span>
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
          className="min-h-[326px] resize-none bg-[#efefef99] shadow-md"
          readOnly={true}
          value={mosDetailData?.detailContent}
        ></Textarea>
      </div>
      <div className="container max-w-[750px]">
        <br />
        <div>
          <Input
            className="h-[56px] shadow-md"
            type="string"
            value={inputVal}
            onKeyDown={addComment}
            placeholder="댓글을 입력해주세요"
            onChange={onChangeInputVal}
          />
        </div>
        <br />
        {coments.map((item, idx) => {
          return (
            <CardComments
              key={idx}
              comentId={item.comentId}
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
