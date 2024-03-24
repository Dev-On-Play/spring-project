import { Terminal } from "lucide-react";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

export function CommonAlert({ props }: { [key: string]: any }) {
  return (
    <Alert variant={props.variant}>
      <Terminal className="h-4 w-4" />
      <AlertTitle>{props.title}</AlertTitle>
      <AlertDescription>{props.contents}</AlertDescription>
    </Alert>
  );
}
