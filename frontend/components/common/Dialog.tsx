"use client"

import { NextPage } from "next"
import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import CommonButton from "@/components/common/Button"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"

interface Props {
    triggerText: string,
    dialogTitle: string,
    dialogLabelText: string,
    className: string
}

const CommonDialog: NextPage<Props> = ({
    triggerText,
    dialogTitle,
    dialogLabelText,
    className
}) => {
    const clickEvent = () => {

    }
    return (
        <Dialog>
            <DialogTrigger asChild className={className}>
                <CommonButton variant="outline" text={triggerText}></CommonButton>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>{dialogTitle}</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="name" className="text-right">
                            {dialogLabelText}
                        </Label>
                        <Input id="name" value="" className="col-span-3" />
                    </div>
                </div>
                <DialogFooter>
                    <CommonButton text="save"></CommonButton>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    )
}

export default CommonDialog