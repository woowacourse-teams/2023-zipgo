import {
  CSSProperties,
  forwardRef,
  memo,
  useEffect,
  useImperativeHandle,
  useRef,
  useState,
} from 'react';
import { styled } from 'styled-components';

import theme from '@/styles/theme';
import { StyledProps } from '@/types/common/utility';

interface NavData {
  title: string;
  onClick?: VoidFunction;
}

interface NavigationBarProps {
  navData: NavData[];
  onChangeNav: (index: number) => void;
  navIndex: number;
  fixedWidth?: boolean;
  indicatorColor?: string;
  style?: CSSProperties;
}

interface NavigationBarMethod {
  snapToIndex: (index: number) => void;
  snapToNext: VoidFunction;
  snapToPrev: VoidFunction;
}

type NavigationBar = NavigationBarMethod;

const NavigationBarComponent = forwardRef<NavigationBarMethod, NavigationBarProps>(
  (navigationBarProps, ref) => {
    useImperativeHandle(ref, () => ({
      snapToIndex,
      snapToNext,
      snapToPrev,
    }));

    const {
      navData,
      navIndex,
      fixedWidth = true,
      indicatorColor = theme.color.primary,
      style,
      onChangeNav,
    } = navigationBarProps;

    const [currentIndex, setCurrentIndex] = useState<number>(navIndex);

    const [currentItemLeft, setCurrentItemLeft] = useState<number>(0);
    const [currentItemWidth, setCurrentItemWidth] = useState<number>(0);

    const snapToIndex = (index: number) => {
      if (index >= 0 && index < navData.length) {
        setCurrentIndex(index);
      }
    };

    const snapToNext = () => {
      setCurrentIndex(prev => prev + 1);
    };

    const snapToPrev = () => {
      setCurrentIndex(prev => prev - 1);
    };

    const onClickNavItem = (nav: NavData, index: number) => {
      nav.onClick && nav.onClick();
      setCurrentIndex(index);
    };

    const currentItemRef = useRef<HTMLUListElement>(null);

    useEffect(() => {
      onChangeNav(currentIndex);

      const item = currentItemRef.current;
      if (item) {
        const childNode = currentItemRef.current.childNodes[currentIndex] as HTMLButtonElement;
        if (childNode) {
          setCurrentItemLeft(
            childNode.getBoundingClientRect().left - item.getBoundingClientRect().left,
          );
          setCurrentItemWidth(childNode.getBoundingClientRect().width);
        }
      }
    }, [currentIndex, onChangeNav]);

    return (
      <NavWrapper style={style} $fixedWidth={fixedWidth}>
        <NavList ref={currentItemRef} $fixedWidth={fixedWidth}>
          {navData.map((nav, index) => (
            <NavItem
              type="button"
              key={index}
              onClick={() => onClickNavItem(nav, index)}
              $fixedWidth={fixedWidth}
            >
              <NavItemTitle $clicked={currentIndex === index}>{nav.title}</NavItemTitle>
            </NavItem>
          ))}
        </NavList>
        <CurrentNavIndicator
          $fixedWidth={fixedWidth}
          $indicatorColor={indicatorColor}
          $dataLength={navData.length}
          $left={currentItemLeft}
          $width={currentItemWidth}
        />
      </NavWrapper>
    );
  },
);

NavigationBarComponent.displayName = 'NavigationBar';

const NavigationBar = memo(NavigationBarComponent);

export default NavigationBar;

interface PropsWithFixedWith extends StyledProps<Pick<NavigationBarProps, 'fixedWidth'>> {}

interface NavIndicatorProps
  extends StyledProps<
    Pick<NavigationBarProps, 'fixedWidth' | 'indicatorColor'> & {
      left: number;
      width: number;
      dataLength: number;
    }
  > {}

interface PropsWithClicked extends StyledProps<{ clicked: boolean }> {}

const NavWrapper = styled.nav<PropsWithFixedWith>`
  position: relative;

  display: flex;
  align-items: center;

  width: 100%;
  height: 4.58rem;

  border-bottom: ${({ $fixedWidth, theme }) =>
    `${$fixedWidth ? '3px' : '1px'} solid ${theme.color.grey250}`};
`;

const CurrentNavIndicator = styled.div<NavIndicatorProps>`
  position: absolute;
  bottom: ${({ $fixedWidth }) => ($fixedWidth ? '-0.3rem' : '-0.1rem')};
  ${({ $left }) => `left: ${$left}px`};

  width: ${({ $dataLength, $width, $fixedWidth }) =>
    $fixedWidth || $width === 0 ? `calc(100% / ${$dataLength})` : `${$width}px`};
  height: 0.3rem;

  background-color: ${({ $indicatorColor }) => $indicatorColor};

  transition: all 200ms ease-in-out;
`;

const NavList = styled.ul<PropsWithFixedWith>`
  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;

  list-style: none;

  ${({ $fixedWidth }) => !$fixedWidth && 'gap: 2rem; padding: 0 2rem;'}
`;

const NavItem = styled.button<PropsWithFixedWith>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  ${({ $fixedWidth }) => $fixedWidth && 'width: 100%'};
  height: 100%;

  background-color: transparent;
  border: none;
  outline: none;
`;

const NavItemTitle = styled.h3<PropsWithClicked>`
  font-size: 1.8rem;
  font-weight: ${({ theme, $clicked }) => ($clicked ? 500 : 400)};
  line-height: 1.8rem;
  color: ${({ theme, $clicked }) => ($clicked ? theme.color.grey700 : theme.color.grey400)};
  letter-spacing: -0.05rem;

  outline: none;

  transition: all 200ms ease-in-out;
`;
