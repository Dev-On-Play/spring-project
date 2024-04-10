"use client";

import CommonAvatar from "@/components/common/Avatar";
import CommonDialog from "@/components/common/Dialog";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";

export default function MyPage() {
  return (
    <div className="flex space-x-4">
      <CommonAvatar className="size-20" imgSrc={"https://github.com/shadcn.png"} />
      <div className="space-y-4">
        <div className="flex space-x-6">
          <CommonDialog className="" triggerText="Nickname" dialogTitle="Edit Nickname" dialogLabelText="Name"></CommonDialog>
          <div className="flex flex-1 self-center space-x-2">
            <Switch />
            <div className="self-start">
              <Label>온라인</Label>
            </div>
          </div>
        </div>
        <CommonDialog className="" triggerText="Introduce Myself" dialogTitle="Edit Introduce" dialogLabelText="Introduce"></CommonDialog>
      </div>
    </div>
  )
}