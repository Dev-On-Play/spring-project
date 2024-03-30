import { useEffect, useState } from "react"
import { NextPage } from "next"
import { Calendar } from "@/components/ui/calendar"

interface Props {
  date: Date | undefined
  changeDate: any
}

const CommonCalendar: NextPage<Props> = ({ date = new Date(), changeDate }) => {
  const [defaultDate, setDefaultDate] = useState<Date | undefined>(date)
  useEffect(() => {
    changeDate(defaultDate)
  }, [defaultDate, changeDate])
  return (
    <Calendar
      mode="single"
      selected={defaultDate}
      onSelect={setDefaultDate}
      className="rounded-md border"
    />
  )
}

export default CommonCalendar
