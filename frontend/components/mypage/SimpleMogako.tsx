"use client";
import {NextPage} from "next";

interface Props {
    title: string,
    category: string,
    date: string,
    min: string,
    max: string,
    dueDate?: string,
    visibility: string
}

const SimpleMogako: NextPage<Props> = ({
                                           title,
                                           category,
                                           date,
                                           min,
                                           max,
                                           dueDate,
                                           visibility
                                       }) => {
    return (
        <div className="flex mt-6 items-center">
            <div className="border-2 rounded-[25px] border-[#A1A1AA] flex-1 mr-5">
                <div className="flex p-2 justify-around items-center min-h-[52px]">
                    <div className="min-w-[300px] min-h-[28px] text-[12px] text-center place-content-center border-2 rounded-[15px] border-[#A1A1AA]">
                        {title}
                    </div>
                    <div className="min-w-[70px] min-h-[28px] text-[12px] text-center place-content-center border-2 rounded-[15px] border-[#A1A1AA]">
                        {category}
                    </div>
                    <div className="min-w-[90px] min-h-[28px] text-[12px] text-center place-content-center border-2 rounded-[15px] border-[#A1A1AA]">
                        {date}
                    </div>
                    <div className="min-w-[70px] min-h-[28px] text-[12px] text-center place-content-center border-2 rounded-[15px] border-[#A1A1AA]">
                        {min}/{max}
                    </div>
                </div>
            </div>
            <div className={visibility}>
                <div
                    className="min-w-[60px] min-h-[28px] font-bold text-[12px] bg-themeColor3 rounded-[8px] text-black text-center place-content-center">
                    {dueDate}
                </div>
            </div>
        </div>
    );
}

export default SimpleMogako