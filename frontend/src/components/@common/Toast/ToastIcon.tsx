import ToastInfoIcon from '@/assets/svg/toast_info_icon.svg';
import ToastSuccessIcon from '@/assets/svg/toast_success_icon.svg';
import ToastWarningIcon from '@/assets/svg/toast_warning_icon.svg';
import { ToastIconProps } from '@/types/toast/client';

const ToastIcon = (props: ToastIconProps) => {
  const { type } = props;

  if (type === 'info') {
    return <img src={ToastInfoIcon} alt="정보" />;
  }
  if (type === 'warning') {
    return <img src={ToastWarningIcon} alt="경고" />;
  }
  if (type === 'success') {
    return <img src={ToastSuccessIcon} alt="성공" />;
  }

  throw new Error('토스트의 타입을 설정해주세요.');
};

export default ToastIcon;
