import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import BackBtnIcon from '@/assets/svg/back_btn.svg';
import Template from '@/components/@common/Template';
import PetProfileEditionForm from '@/components/PetProfile/PetProfileEditionForm/PetProfileEditionForm';
import { routerPath } from '@/router/routes';

const PetProfileEdition = () => {
  const navigate = useNavigate();

  const goBack = (): void => navigate(routerPath.back);

  return (
    <Template.WithoutHeader footer={false}>
      <StaticHeader>
        <BackButtonWrapper type="button" onClick={goBack} aria-label="뒤로가기">
          <BackBtnImage src={BackBtnIcon} alt="뒤로가기 아이콘" />
        </BackButtonWrapper>
      </StaticHeader>
      <PetProfileEditionForm />
    </Template.WithoutHeader>
  );
};

export default PetProfileEdition;

const StaticHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 4rem;
  padding: 2.8rem;
`;

const BackButtonWrapper = styled.button`
  cursor: pointer;

  position: absolute;
  top: 2rem;
  left: 0.8rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 4rem;
  height: 4rem;

  background-color: transparent;
  border: none;
`;

const BackBtnImage = styled.img`
  width: 3.2rem;
  height: 3.2rem;

  object-fit: cover;
`;
