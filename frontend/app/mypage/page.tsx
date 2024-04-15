"use client";

import CommonAvatar from "@/components/common/Avatar";
import CommonDialog from "@/components/common/Dialog";
import {Label} from "@/components/ui/label";
import {Switch} from "@/components/ui/switch";
import SimpleMogako from "@/components/mypage/SimpleMogako"

export default function MyPage() {
    const items = [
        {
            title: 'testTitle1',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle2',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle3',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle4',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle5',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle6',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle7',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle8',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle9',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle10',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        },
        {
            title: 'testTitle11',
            category: 'testCategory',
            date: '2024-04-14',
            min: '1',
            max: '4',
            dueDate: 'D-100',
            visibility: 'visible'
        }
    ]
    return (
        <div>
            <div className="container min-w-[650px] flex mt-10">
                <div className="mr-5 content-center">
                    <CommonAvatar className="size-20" imgSrc={"https://github.com/shadcn.png"}/>
                </div>
                <div className="flex-1 space-y-4 ml-5">
                    <div className="flex space-x-6">
                        <CommonDialog className="min-w-[200px]" triggerText="Nickname" dialogTitle="Edit Nickname" dialogLabelText="Name"></CommonDialog>
                        <div className="flex flex-1 self-center space-x-2">
                            <Switch/>
                            <div className="self-start">
                                <Label>온라인</Label>
                            </div>
                        </div>
                    </div>
                    <CommonDialog className="min-w-[400px]" triggerText="Introduce Myself" dialogTitle="Edit Introduce" dialogLabelText="Introduce"></CommonDialog>
                </div>
            </div>
            <div className="container min-w-[750px] flex flex-col mt-10">
                <div
                    className="bg-themeColor3 max-w-[200px] min-h-[48px] rounded-[8px] text-black text-center place-content-center">
                    참여 예정인 모각코
                </div>
                {items.map((item, idx) => {
                    return (
                        <SimpleMogako title={item.title} category={item.category} date={item.date} min={item.min} max={item.max} dueDate={item.dueDate} visibility={item.visibility} ></SimpleMogako>
                    )
                })}
            </div>
            <div className="container max-w-[750px] flex flex-col mt-10">
                <div
                    className="bg-themeColor3 max-w-[200px] min-h-[48px] rounded-[8px] text-black text-center place-content-center">
                    참여 중인 모각코
                </div>
                {items.map((item, idx) => {
                    return (
                        <SimpleMogako title={item.title} category={item.category} date={item.date} min={item.min} max={item.max} dueDate={item.dueDate} visibility={item.visibility} ></SimpleMogako>
                    )
                })}
            </div>
            <div className="container max-w-[750px] flex flex-col mt-10">
                <div
                    className="bg-themeColor3 max-w-[200px] min-h-[48px] rounded-[8px] text-black text-center place-content-center">
                    참여한 모각코
                </div>
                {items.map((item, idx) => {
                    return (
                        <SimpleMogako title={item.title} category={item.category} date={item.date} min={item.min} max={item.max} dueDate={item.dueDate} visibility={item.visibility} ></SimpleMogako>
                    )
                })}
            </div>
        </div>
    )
}