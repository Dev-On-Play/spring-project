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
import { Button } from "@/components/ui/button"

interface Props {
  callText: string
  dialogTitle: string
  dialogText: string
  isCancell: boolean
  cancellText: string
  confirmText: string
}

const CommonAlertDialog: NextPage<Props> = ({
  callText,
  dialogTitle,
  dialogText,
  isCancell,
  cancellText,
  confirmText,
}) => {
  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        <Button variant="outline">{callText}</Button>
      </AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>{dialogTitle}</AlertDialogTitle>
          <AlertDialogDescription>{dialogText}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          {isCancell ? (
            <AlertDialogCancel>{cancellText}</AlertDialogCancel>
          ) : (
            <></>
          )}
          <AlertDialogAction>{confirmText}</AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  )
}

export default CommonAlertDialog
