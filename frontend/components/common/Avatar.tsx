import { NextPage } from "next"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"

interface Props {
  imgSrc: string
  alt?: string
  fallback?: string
}

const CommonAvatar: NextPage<Props> = ({
  imgSrc,
  alt = "@shadcn",
  fallback = "CN",
}) => {
  return (
    <Avatar>
      <AvatarImage src={imgSrc} alt={alt} />
      <AvatarFallback>{fallback}</AvatarFallback>
    </Avatar>
  )
}

export default CommonAvatar
