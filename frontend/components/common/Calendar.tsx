import {useEffect, useState} from "react"
import {NextPage} from "next"
import {Calendar} from "@/components/ui/calendar"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

interface Props {
    date: Date | undefined
    changeDate: any
}

const CommonCalendar: NextPage<Props> = ({date = new Date(), changeDate}) => {
    const [defaultDate, setDefaultDate] = useState<Date | undefined>(date)
    useEffect(() => {
        changeDate(defaultDate)
    }, [defaultDate, changeDate])
    const setHour = (hour: string) => {
        setDefaultDate(new Date(defaultDate!!.setHours(parseInt(hour))))
    }
    const setMinute = (minute: string) => {
        setDefaultDate(new Date(defaultDate!!.setMinutes(parseInt(minute))))
    }
    return (
        <div>
            <Calendar
                mode="single"
                selected={defaultDate}
                onSelect={setDefaultDate}
                className="rounded-md border"
            />
            <div className="flex justify-between">
                <div>
                    <Select onValueChange={setHour}>
                        <SelectTrigger className="w-[110px] text-[12px]">
                            <SelectValue placeholder="Select hour"/>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="1">1</SelectItem>
                            <SelectItem value="2">2</SelectItem>
                            <SelectItem value="3">3</SelectItem>
                            <SelectItem value="4">4</SelectItem>
                            <SelectItem value="5">5</SelectItem>
                            <SelectItem value="6">6</SelectItem>
                            <SelectItem value="7">7</SelectItem>
                            <SelectItem value="8">8</SelectItem>
                            <SelectItem value="9">9</SelectItem>
                            <SelectItem value="10">10</SelectItem>
                            <SelectItem value="11">11</SelectItem>
                            <SelectItem value="12">12</SelectItem>
                        </SelectContent>
                    </Select>
                </div>
                <div>
                    <Select onValueChange={setMinute}>
                        <SelectTrigger className="w-[120px] text-[12px]">
                            <SelectValue placeholder="select minute"/>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="0">00</SelectItem>
                            <SelectItem value="5">05</SelectItem>
                            <SelectItem value="10">10</SelectItem>
                            <SelectItem value="15">15</SelectItem>
                            <SelectItem value="20">20</SelectItem>
                            <SelectItem value="25">25</SelectItem>
                            <SelectItem value="30">30</SelectItem>
                            <SelectItem value="35">35</SelectItem>
                            <SelectItem value="40">40</SelectItem>
                            <SelectItem value="45">45</SelectItem>
                            <SelectItem value="50">50</SelectItem>
                            <SelectItem value="55">55</SelectItem>
                        </SelectContent>
                    </Select>
                </div>
            </div>
        </div>
    )
}

export default CommonCalendar
