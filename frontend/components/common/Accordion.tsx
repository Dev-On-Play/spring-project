"use client";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import React from "react";
export default function CommonAccordion({
  props,
}: {
  title?: string;
  contents?: string;
  [key: string]: any;
}) {
  return (
    <Accordion type="single" collapsible>
      <AccordionItem value="item-1">
        <AccordionTrigger>{props.title}</AccordionTrigger>
        <AccordionContent>{props.contents}</AccordionContent>
      </AccordionItem>
    </Accordion>
  );
}
