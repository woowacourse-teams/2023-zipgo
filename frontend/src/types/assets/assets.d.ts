declare module '*.svg';
declare module '*.woff';
declare module '*.woff2';
declare module '*.eot';
declare module '*.ttf';
declare module '*.otf';
declare module '*.jpg';
declare module '*.jpeg';
declare module '*.png';
declare module '*.webp';

declare module '*responsive' {
  const content: ResponsiveImageOutput;
  export default content;
}

interface ResponsiveImageOutput {
  src: string;
  srcSet: string;
  placeholder: string | undefined;
  images: { path: string; width: number; height: number }[];
  width: number;
  height: number;
  toString: () => string;
}
