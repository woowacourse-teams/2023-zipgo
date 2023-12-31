import { css, keyframes as styledKeyframes } from 'styled-components';

// 색상 팔레트
const color = {
  primary: '#3E5E8E',
  secondary: '#7DA3C2',
  warning: '#E73846',
  blue: '#D0E6F9',
  lightRed: '#FFDBDE',

  white: '#FFFFFF',
  grey100: '#DCDEE1',
  grey200: '#F2F4F6',
  grey250: '#C7CFD7',
  grey300: '#AFB8C1',
  grey400: '#8B95A1',
  grey500: '#4E5968',
  grey600: '#3E4753',
  grey700: '#333D4B',
  grey800: '#333D4B',
  black: '#1C1D20',
} as const;

const maxWidth = {
  mobile: '49rem',
} as const;

// 폰트
const font = {
  heading:
    'system-ui, -apple-system, BlinkMacSystemFont, "Open Sans", "Helvetica Neue", sans-serif',
  body: 'system-ui, -apple-system, BlinkMacSystemFont, "Open Sans", "Helvetica Neue", sans-serif',
  // 추가적인 폰트 정의
} as const;

// 그림자 스타일
const shadow = {
  type1: '0px 0px 2px rgba(0, 0, 0, 0.2)',
  type2: '0px 8px 20px rgba(0, 0, 0, 0.1)',

  top1: '0 -12px 12px -12px rgba(0,0,0,0.16)',
} as const;

// 컴포넌트 스타일
const componentStyle = {
  button: {
    padding: '10px 20px',
    borderRadius: '4px',
    fontWeight: 'bold',
  },
  input: {
    border: '1px solid #CCCCCC',
    borderRadius: '4px',
    padding: '8px',
  },

  backDrop: {
    position: 'fixed',
    top: 0,

    width: '100%',
    maxWidth: maxWidth.mobile,
    height: 'calc(var(--vh, 1vh) * 100)',

    background: 'rgba(0, 0, 0, 0.50)',
  },
} as const;

const keyframes = {
  bottomSheetAppear: styledKeyframes`
   from {
      transform: translateY(100%);
    }

    to {
      transform: translateY(0%);
    }
  `,

  shiny: styledKeyframes`
    0% {
        transform: scale(0) rotate(45deg);
        opacity: 0;
    }

    80% {
      transform: scale(0) rotate(45deg);
      opacity: 0.5;
    }

    81% {
      transform: scale(4) rotate(45deg);
      opacity: 1;
    }

    100% {
      transform: scale(50) rotate(45deg);
      opacity: 0;
    }
  `,

  shimmer: styledKeyframes`
    to {
        background-position: 200% 0;
      }
  `,
} as const;

const animation = {
  skeleton: css`
    background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
    background-size: 200% 100%;
    border-radius: 8px;

    animation: ${keyframes.shimmer} 1.5s infinite;
  `,
} as const;

// 테마 객체
const theme = {
  color,
  maxWidth,
  font,
  shadow,
  componentStyle,
  keyframes,
  animation,
};

export default theme;
