/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,

  //타입스크립트 적용
  typescript: {
    ignoreBuildErrors: false,
    tsconfigPath: "tsconfig.json",
  },
  distDir: ".next",
  async rewrites() {
    return [
      {
        source: `${process.env.NEXT_PUBLIC_BASE_URL}/:path*`,
        destination: `${process.env.NEXT_PUBLIC_BACKEND_URL}${process.env.NEXT_PUBLIC_BASE_URL}/:path*`,
      },
    ]
  },
}

export default nextConfig
