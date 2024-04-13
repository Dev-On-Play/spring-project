import { NextPage } from "next"
import CommonAvatar from "@/components/common/Avatar"
import CommonButton from "@/components/common/Button"

interface CommentType {
  contents: string
  profile?: string
  nickName?: string
  createDate?: string | Date
}
interface Props extends CommentType {
  className?: string
  childComment?: CommentType[]
}

const CardComments: NextPage<Props> = ({
  contents,
  profile = "https://github.com/shadcn.png",
  nickName,
  createDate,
  childComment,
  className,
}) => {
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
            fontSize={"text-[12px]"}
            variant={"default"}
          />
        )}
      </div>
      {/* 댓글이 달려있지 않을 경우에 버튼 노출 */}
      {childComment && childComment.length > 0 ? (
        childComment.map((item, idx) => {
          return (
            <div className="relative" key={idx}>
              <svg
                className="absolute left-4 top-3"
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  fillRule="evenodd"
                  clipRule="evenodd"
                  d="M12.293 16.707C12.1055 16.5194 12.0002 16.2651 12.0002 16C12.0002 15.7348 12.1055 15.4805 12.293 15.293L14.586 13H8.99997C7.14345 13 5.36298 12.2625 4.05022 10.9497C2.73747 9.63696 1.99997 7.85649 1.99997 5.99997V3.99997C1.99997 3.73475 2.10533 3.4804 2.29286 3.29286C2.4804 3.10533 2.73475 2.99997 2.99997 2.99997C3.26519 2.99997 3.51954 3.10533 3.70708 3.29286C3.89461 3.4804 3.99997 3.73475 3.99997 3.99997V5.99997C3.99997 7.32605 4.52675 8.59782 5.46444 9.5355C6.40212 10.4732 7.67389 11 8.99997 11H14.586L12.293 8.70697C12.1975 8.61472 12.1213 8.50438 12.0689 8.38237C12.0165 8.26037 11.9889 8.12915 11.9877 7.99637C11.9866 7.86359 12.0119 7.73191 12.0621 7.60902C12.1124 7.48612 12.1867 7.37447 12.2806 7.28057C12.3745 7.18668 12.4861 7.11243 12.609 7.06215C12.7319 7.01187 12.8636 6.98656 12.9964 6.98772C13.1291 6.98887 13.2604 7.01646 13.3824 7.06887C13.5044 7.12128 13.6147 7.19746 13.707 7.29297L17.707 11.293C17.8944 11.4805 17.9998 11.7348 17.9998 12C17.9998 12.2651 17.8944 12.5194 17.707 12.707L13.707 16.707C13.5194 16.8944 13.2651 16.9998 13 16.9998C12.7348 16.9998 12.4805 16.8944 12.293 16.707Z"
                  fill="#3F3F46"
                />
              </svg>
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
