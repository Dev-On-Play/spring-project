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
import CardComments, {
  MogakosComment,
  MogakosCommentType,
} from "@/components/card/Comments"
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

const DetailPage: NextPage<Props> = ({}) => {
  const coments = Array.from({ length: 30 }, () => 0).map(
    (item, idx): MogakosComment => {
      return {
        mogako_id: 1, //모각코아이디
        id: 0,
        contents: `댓글 영역입니다 ${idx + 1}`,
        member: {
          id: 0,
          profile: "https://github.com/shadcn.png",
          nickname: `닉네임${idx + 1}`,
        },
        created_date: `2024-04-${idx + 1} 00:00:00`,
        childComments: {
          childList: [
            {
              id: 0,
              mogako_id: 1,
              member: {
                id: 0,
                profile: "https://github.com/shadcn.png",
                nickname: `닉네임${idx + 1}`,
              },
              parents_id: 1,
              contents: "대댓글 내용입니다",
              created_date: `2024-04-${idx + 1} 00:00:00`,
            },
          ],
        },
      }
    }
  )

  const [inputVal, onChangeInputVal, setInputVal] = useInput("")
  const [mosDetailData, setMosDetailData] = useState<MosDetailType>()
  const [mosComment, setMosComment] = useState<MogakosCommentType>()
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
          .then(async (res) => {
            console.log("상세조회 결과 : ", res.data)
            await getComment(mogakoId)
            setMosDetailData({ ...res.data })
            offLoading()
          })
          .catch((err) => {
            offLoading()
          })
      }
    },
    [offLoading, onLoading]
  )
  const getComment = async (mogakoId: string) => {
    await fetchApi(`/mogakos/${mogakoId}/comments`, {
      method: "get",
    })
      .then((res) => {
        setMosComment({ ...res.data })
      })
      .catch((err) => {
        offLoading()
      })
  }
  // 화면 랜더링 이후 모각코 상세 내역 조회
  useEffect(() => {
    getDetail(params.id as string)
  }, [params.id, onLoading, offLoading, getDetail])
  const addComment = async (e: any) => {
    if (e.code === "Enter") {
      onLoading()
      await fetchApi(`/mogakos/${params.id}/comments/create`, {
        method: "post",
        data: {
          mogako_id: params.id,
          member_id: 0, // 로그인 계정확인
          parent_id: params.id, // ??? 새댓글일때는 부모 아이디가 없음
          contents: inputVal,
        },
      })
        .then(async (res) => {
          await getDetail(params.id as string)
        })
        .catch((err) => {})
    }
  }
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
        {mosComment?.comments && mosComment.comments.length > 0 ? (
          mosComment?.comments.map((item, idx) => {
            return (
              <CardComments
                key={idx}
                mogakosComment={item}
                // childComment={idx % 2 === 0 ? [coments[idx]] : []}
              />
            )
          })
        ) : (
          <div className="container max-w-[750px] text-center min-h-96">
            댓글이 없습니다
          </div>
        )}
      </div>
    </>
  )
}

export default DetailPage
