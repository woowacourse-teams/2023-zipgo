// 색상 팔레트
const color = {
  primary: '#3E5E8E',
  secondary: '#7DA3C2',
  background: '#F2F5FA',
  text: '#333333',
  success: '#64B967',
  error: '#E74C3C',
  // 추가적인 색상 정의
};

// 폰트
const font = {
  heading:
    'system-ui, -apple-system, BlinkMacSystemFont, "Open Sans", "Helvetica Neue", sans-serif',
  body: 'system-ui, -apple-system, BlinkMacSystemFont, "Open Sans", "Helvetica Neue", sans-serif',
  // 추가적인 폰트 정의
};

// 그림자 스타일
const shadow = {
  type1: '0px 0px 2px rgba(0, 0, 0, 0.2)',
  type2: '0px 8px 20px rgba(0, 0, 0, 0.1)',

  top1: '0 -12px 12px -12px rgba(0,0,0,0.16)',
};

// 컴포넌트 스타일
const componentStyle = {
  button: {
    padding: '10px 20px',
    borderRadius: '4px',
    fontWeight: 'bold',
    // 추가적인 버튼 스타일 정의
  },
  input: {
    border: '1px solid #CCCCCC',
    borderRadius: '4px',
    padding: '8px',
    // 추가적인 인풋 스타일 정의
  },
  // 추가적인 컴포넌트 스타일 정의
};

// 테마 객체
const theme = {
  color,
  font,
  shadow,
  componentStyle,
};

export default theme;
