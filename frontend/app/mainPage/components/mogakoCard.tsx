import React from "react"
import { Button } from "@/components/ui/button"
import { Card, CardHeader, CardTitle } from "@/components/ui/card"

// Define props type for the component
type CardProps = {
  profileImageUrl: string
  nickname: string
  title: string
  date: string
  category: string
  minPeople: number
  currentPeople: number
  maxPeople: number
  hashtags: string[]
  content: string
}

// Functional Component
const MogakoInfoCard: React.FC<CardProps> = ({
  profileImageUrl,
  nickname,
  title,
  date,
  category,
  minPeople,
  currentPeople,
  maxPeople,
  hashtags,
  content,
}) => {
  return (
    <>
      <Card>
        <CardHeader>
          <div className="col-span-1 text-slate-600 rounded-md max-w-96">
            <div className="row-span-1">
              <div className="grid grid-cols-7 items-center">
                <div>
                  <div className="items-center rounded-full size-12">
                    <img src={profileImageUrl} alt="Profile" />
                    <div className="text-xs text-center overflow-clip">
                      {nickname}
                    </div>
                  </div>
                </div>

                <div className="col-span-5 ml-1">
                  <CardTitle>
                    <div className="rounded-full border text-base mb-1 p-1 text-center">
                      {title}
                    </div>
                  </CardTitle>
                  <div className="justify-between flex gap-1 my-1">
                    <div className="rounded-full border text-[0.6rem] p-1 text-center">
                      {date}
                    </div>
                    <div className="rounded-full border text-xs p-1 text-center">
                      {category}
                    </div>
                    <div className="rounded-full border text-xs p-1 text-center">
                      최소 {minPeople}명
                    </div>
                    <div className="rounded-full border text-xs p-1 text-center">
                      {currentPeople} / {maxPeople}
                    </div>
                  </div>
                  <div className="border rounded-full text-center p-1 text-[0.6rem]">
                    {hashtags.map((tag) => `#${tag} `)}
                  </div>
                </div>
                <div className="ml-2">
                  <Button className="rounded-full text-xs">참여</Button>
                </div>
              </div>
            </div>
          </div>
        </CardHeader>

        <div className="row-span p-2 -mt-4">
          <p className="text-xs p-1 border rounded-lg bg-slate-100 max-h-24 overflow-hidden">
            {content}
          </p>
        </div>
      </Card>
    </>
  )
}

export default MogakoInfoCard
