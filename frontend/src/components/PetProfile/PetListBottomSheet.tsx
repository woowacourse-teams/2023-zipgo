import { styled } from 'styled-components';

import PetProfileProvider from '@/context/petProfile/PetProfileContext';

import { Dialog } from '../@common/Dialog/Dialog';
import UserProfile from '../@common/Header/UserProfile';
import QueryBoundary from '../@common/QueryBoundary';
import PetList from './PetList';

const PetListBottomSheet = () => (
  <QueryBoundary>
    <Dialog>
      <PetProfileProvider>
        <UserProfile />
        <Dialog.Portal>
          <Dialog.BackDrop />
          <Dialog.Content asChild>
            {({ openHandler }) => <PetListContainer toggleDialog={openHandler} />}
          </Dialog.Content>
        </Dialog.Portal>
      </PetProfileProvider>
    </Dialog>
  </QueryBoundary>
);

interface PetListContainerProps {
  toggleDialog: VoidFunction;
}

const PetListContainer = (props: PetListContainerProps) => {
  const { toggleDialog } = props;

  return (
    <Layout>
      <DialogHeader>어떤 아이 식품을 찾으세요?</DialogHeader>
      <DialogContent>
        <PetList />
      </DialogContent>
      <ButtonWrapper>
        <CloseButton type="button" onClick={toggleDialog}>
          닫기
        </CloseButton>
      </ButtonWrapper>
    </Layout>
  );
};

export default PetListBottomSheet;

const Layout = styled.div`
  position: fixed;
  z-index: 1001;
  bottom: 0;

  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: flex-start;

  width: 100vw;
  height: 57.4rem;

  background: ${({ theme }) => theme.color.grey200};
  border-radius: 20px 20px 0 0;

  animation: ${({ theme }) => theme.animation.bottomSheetAppear} 0.5s cubic-bezier(0.2, 0.6, 0.3, 1);
`;

const DialogHeader = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;

  width: 100%;
  height: 6rem;
  padding: 0 2rem;

  font-size: 1.8rem;
  font-weight: 500;
  line-height: 2rem;
  color: ${({ theme }) => theme.color.grey600};
  list-style: none;

  background-color: ${({ theme }) => theme.color.white};
  border-bottom: 1px solid ${({ theme }) => theme.color.grey250};
  border-radius: 20px 20px 0 0;
`;

const DialogContent = styled.div<{
  $paddingBottom?: string;
}>`
  position: relative;

  flex-grow: 1;

  width: 100%;
  padding: 2rem;

  background: ${({ theme }) => theme.color.grey200};
`;

const ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 10rem;
  padding: 2rem;

  background-color: ${({ theme }) => theme.color.white};
  border-top: 1px solid ${({ theme }) => theme.color.grey250};
`;

const CloseButton = styled.button`
  cursor: pointer;

  width: 100%;
  height: 5.1rem;

  font-size: 1.6rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: 0.02rem;

  background-color: ${({ theme }) => theme.color.primary};
  border: none;
  border-radius: 16px;
  box-shadow: 0 -8px 20px #fff;

  transition: all 100ms ease-in-out;

  &:active {
    scale: 0.98;
  }

  &:disabled {
    cursor: not-allowed;

    background-color: ${({ theme }) => theme.color.grey300};
  }
`;
