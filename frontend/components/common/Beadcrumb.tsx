"use client"

import { NextPage } from "next"
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb"

interface BreadcurmbType {
  routerUrl?: string
  text: string
}
interface CrumbContentsType extends BreadcurmbType {
  index: number
  len: number
}
interface Props {
  items: BreadcurmbType[]
}

const CommonBeadcrumb: NextPage<Props> = ({ items }) => {
  return (
    <Breadcrumb>
      <BreadcrumbList>
        {items.map((item, idx) => {
          return (
            <CrumbContents
              text={item.text}
              routerUrl={item.routerUrl}
              index={idx}
              len={items.length}
              key={idx}
            />
          )
        })}
      </BreadcrumbList>
    </Breadcrumb>
  )
}
const CrumbContents: NextPage<CrumbContentsType> = ({
  text,
  routerUrl,
  index,
  len,
}) => {
  return (
    <>
      <BreadcrumbItem>
        {routerUrl ? (
          <BreadcrumbLink href={routerUrl}>{text}</BreadcrumbLink>
        ) : (
          <BreadcrumbPage>{text}</BreadcrumbPage>
        )}
      </BreadcrumbItem>
      {len - 1 !== index ? <BreadcrumbSeparator /> : <></>}
    </>
  )
}
export default CommonBeadcrumb
