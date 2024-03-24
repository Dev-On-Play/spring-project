import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import Link from "next/link";
export default function Component() {
  return (
    <div className="margin-zero-auto">
      <div className="mx-auto max-w-[350px] h-full flex flex-col items-center justify-center">
        <div className="flex-1" />
        <div className="space-y-6 text-center">
          <h1 className="text-3xl font-bold">소셜 로그인</h1>
          <div className="mt-4 text-center text-sm">
            <Link className="underline" href="#">
              모각코 모임장 으로
            </Link>
            <span> | </span>
            <Link className="underline bg-yellow-500" href="#">
              모각코 회원 으로
            </Link>
          </div>
          <p className="text-gray-500 dark:text-gray-400"></p>
        </div>
        <div>
          <div className="space-y-4">
            <p className="text-sm text-center mb-4">
              000님. 로그인 되었습니다.
            </p>
            <p className="text-sm text-center mb-6 underline">
              로그인 일시 : yyyy-mm-dd HH:mm
            </p>
            <div className="flex flex-col space-y-4">
              {/* <Button className="bg-blue-500 text-white py-2 rounded">
                모각코 생성
              </Button>
              <Button className="bg-green-500 text-white py-2 rounded">
                내 모각코 보기
              </Button> */}
              <Button className="bg-blue-500 text-white py-2 rounded">
                내가 가입된 스터디 보러가기
              </Button>
              <Button className="bg-green-500 text-white py-2 rounded">
                스터디 둘러보기
              </Button>
            </div>
          </div>
        </div>
        <div className="flex-1" />
      </div>
    </div>
  );
}
