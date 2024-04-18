"use client"

import { useCallback } from "react"
import { NextPage } from "next"
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog"
import { Button, ButtonProps } from "@/components/ui/button"

interface Props extends ButtonProps {
  callText: string
  dialogTitle: string
  dialogText: string
  isCancell: boolean
  cancellText: string
  confirmText: string
  btnClass?: string
  callBack?: (e: any) => {} | any
  [key: string]: any
  // alertOpen: boolean
}

const CommonAlertDialog: NextPage<Props> = ({
  callText,
  dialogTitle,
  dialogText,
  isCancell,
  cancellText,
  confirmText,
  btnClass,
  variant = "outline",
  fontSize,
  callBack,
  // alertOpen = false,
}) => {
  const btnClick = useCallback(
    (e: boolean) => {
      console.log(e)
      if (callBack) {
        callBack(e)
      }
    },
    [callBack]
  )

  return (
    //  open={alertOpen}
    <AlertDialog>
      <AlertDialogTrigger asChild>
        <Button className={btnClass} size={fontSize} variant={variant}>
          {callText}
        </Button>
      </AlertDialogTrigger>
      <AlertDialogContent className="w-[300px]">
        <AlertDialogHeader>
          <AlertDialogTitle>{dialogTitle}</AlertDialogTitle>
          <AlertDialogDescription>{dialogText}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          {isCancell ? (
            <AlertDialogCancel
              className="shadow-md"
              onClick={() => {
                btnClick(false)
              }}
            >
              {cancellText}
            </AlertDialogCancel>
          ) : (
            <></>
          )}
          <AlertDialogAction
            className="bg-[#32a287] shadow-md hover:bg-themeColor5 "
            onClick={() => {
              btnClick(true)
            }}
          >
            {confirmText}
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  )
}

export default CommonAlertDialog
