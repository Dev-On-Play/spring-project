import { NextPage } from "next"
import CommonAvatar from "../common/Avatar"

interface Props {
  contents: string
  profile?: string
  nickName?: string
  createDate?: string | Date
}

const CardComments: NextPage<Props> = ({
  contents,
  profile = "https://github.com/shadcn.png",
  nickName,
  createDate,
}) => {
  return (
    <div className="relative flex h-[80px] rounded-2xl border border-solid border-[#a1a1aa80]">
      <div className="ml-2 mr-4 flex flex-col items-center justify-center">
        <CommonAvatar imgSrc={profile} alt={nickName} fallback={nickName} />
        <div>{nickName}</div>
      </div>
      <div className="grow">{contents}</div>
      <div className="absolute bottom-0 right-2">{createDate?.toString()}</div>
    </div>
  )
}

export default CardComments
