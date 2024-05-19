"use client"

import CommonButton from "@/components/common/Button";
import {Input} from "@/components/ui/input";
import {Textarea} from "@/components/ui/textarea";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectTrigger,
    SelectValue
} from "@/components/ui/select";
import * as React from "react";
import {Button} from "@/components/ui/button";
import {X} from "lucide-react";
import {useState} from "react";
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover";
import CommonCalendar from "@/components/common/Calendar";

type Tag = { id: number; tagName: string }

export default function CreatePage() {
    const TAG_NAME_LIMIT = 10
    const TAG_COUNT_LIMIT = 5
    const [items, setItems] = useState<Tag[]>([])
    const [tagName, setTagName] = useState('')
    const [count, setCount] = useState(0)
    const onChangeTagEvent = (event: { code: string; }) => {
        if (event.code === 'Enter') {
            if (tagName && tagName.length < TAG_NAME_LIMIT && items.length <= TAG_COUNT_LIMIT) {
                setItems(items.concat({
                    id: count,
                    tagName: tagName
                }))
                setTagName('')
                setCount(count + 1)
            }
        }
    }
    const onTagRemoveClickEvent = (tag: Tag) => {
        setItems(items.filter(t => tag.id !== t.id))
    }
    const [date, onChange] = useState(new Date())
    const day = ['일', '월', '화', '수', '목', '금', '토']
    const formatDigit = (num: number) => String(num).padStart(2, '0')
    const dateFormat = `${date.getFullYear()}-${formatDigit(date.getMonth())}-${formatDigit(date.getDate())} (${day[date.getDay()]}) ${formatDigit(date.getHours())}:${formatDigit(date.getMinutes())}`
    return (
        <div className="container flex min-h-[610px] max-w-[750px] flex-col gap-4">
            <div className="">
                <CommonButton
                    text={"뒤로"}
                    fontSize={"text-[12px]"}
                    className={
                        "start-0 h-[30px] w-[67px] rounded-2xl bg-[#32a287] shadow-md hover:bg-themeColor5"
                    }
                    variant={"default"}
                />
            </div>
            <div className="">
                <Input placeholder="Enter Title" className="h-[52px] w-[750px] text-[26px]"></Input>
            </div>
            <div className="flex w-[750px] items-center gap-8">
                <Popover>
                    <PopoverTrigger className="min-h-[42px] w-[230px] rounded-[12px] border-2 px-8 text-[12px]">
                        {dateFormat}
                    </PopoverTrigger>
                    <PopoverContent>
                        <CommonCalendar changeDate={onChange} date={date}/>
                    </PopoverContent>
                </Popover>
                <div
                    className="h-[32px] min-w-[80px] place-content-center rounded-[10px] bg-themeColor3 text-center text-[14px] text-black">
                    최소 인원
                </div>
                <Select>
                    <SelectTrigger className="w-[130px] text-[10px]">
                        <SelectValue placeholder="Select an option"/>
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectItem value="1">1</SelectItem>
                            <SelectItem value="2">2</SelectItem>
                            <SelectItem value="3">3</SelectItem>
                            <SelectItem value="4">4</SelectItem>
                            <SelectItem value="5">5</SelectItem>
                            <SelectItem value="6">6</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>
                <div
                    className="h-[32px] min-w-[80px] place-content-center rounded-[10px] bg-themeColor3 text-center text-[14px] text-black">
                    최대 인원
                </div>
                <Select>
                    <SelectTrigger className="w-[130px] text-[10px]">
                        <SelectValue placeholder="Select an option"/>
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectItem value="1">1</SelectItem>
                            <SelectItem value="2">2</SelectItem>
                            <SelectItem value="3">3</SelectItem>
                            <SelectItem value="4">4</SelectItem>
                            <SelectItem value="5">5</SelectItem>
                            <SelectItem value="6">6</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>
            </div>
            <div className="flex w-[750px] justify-between">
                <Select>
                    <SelectTrigger className="w-[200px]">
                        <SelectValue placeholder="Select an Category"/>
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectItem value="ai">AI</SelectItem>
                            <SelectItem value="vr">VR</SelectItem>
                            <SelectItem value="backend">백엔드</SelectItem>
                            <SelectItem value="frontend">프론트엔드</SelectItem>
                            <SelectItem value="web">웹</SelectItem>
                            <SelectItem value="android">안드로이드</SelectItem>
                            <SelectItem value="ios">iOS</SelectItem>
                            <SelectItem value="game">게임</SelectItem>
                            <SelectItem value="database">데이터베이스</SelectItem>
                            <SelectItem value="cloud">클라우드</SelectItem>
                            <SelectItem value="bigdata">빅데이터</SelectItem>
                            <SelectItem value="security">보안</SelectItem>
                            <SelectItem value="blockchain">블록체인</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>
                <div className="flex min-w-[430px] content-center rounded-[15px] border-2 border-[#a1a1aa]">
                    {items.map((tag) => {
                        return (
                            <div key={tag.id} className="ml-4 flex h-[35px] items-center">
                                <div className="text-[12px]">
                                    # {tag.tagName}
                                </div>
                                <Button className="ml-1 size-[11px] bg-white hover:bg-white" size="icon" onClick={e => {
                                    onTagRemoveClickEvent(tag)
                                }}> <X className="size-[11px]" color="black"/></Button>
                            </div>
                        )
                    })}
                    {
                        items.length < TAG_COUNT_LIMIT &&
                        <input className="ml-4 h-[35px] flex-1 rounded-[15px] text-[12px] outline-0"
                               placeholder="Enter Tag"
                               value={tagName} onKeyDown={onChangeTagEvent}
                               onChange={(e) => setTagName(e.target.value)}/>
                    }
                </div>
            </div>
            <div className="">
                <Textarea placeholder="Enter your mos description" className="min-h-[325px] min-w-[750px]"/>
            </div>
            <div className="flex w-[750px] flex-row-reverse">
                <CommonButton
                    text={"게시"}
                    fontSize={"text-[12px]"}
                    className={
                        "h-[30px] w-[128px] rounded-2xl bg-[#32a287] shadow-md hover:bg-themeColor5"
                    }
                    variant={"default"}
                />
            </div>
        </div>
    )
}