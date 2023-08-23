import { ChangeEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import EditIconLight from '@/assets/svg/edit_icon_light.svg';
import TrashCanIcon from '@/assets/svg/trash_can_icon.svg';
import Input from '@/components/@common/Input/Input';
import Label from '@/components/@common/Label/Label';
import { PET_SIZES } from '@/constants/petProfile';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { usePetProfileValidation } from '@/hooks/petProfile';
import {
  useEditPetMutation,
  usePetItemQuery,
  useRemovePetMutation,
} from '@/hooks/query/petProfile';
import { PATH } from '@/router/routes';
import { PetSize } from '@/types/petProfile/client';

import PetAgeSelect from '../PetAgeSelect';
import PetInfoInForm from '../PetInfoInForm';

const PetProfileEditionForm = () => {
  const navigate = useNavigate();
  const { petId } = useValidParams(['petId']);
  const { petItem, resetPetItemQuery } = usePetItemQuery({ petId: Number(petId) });
  const { editPetMutation } = useEditPetMutation();
  const { removePetMutation } = useRemovePetMutation();
  const { updatePetProfile, resetPetProfile } = usePetProfile();
  const {
    isValidNameInput,
    isValidAgeSelect,
    isValidWeightInput,
    isMixedBreed,
    validateName,
    validateAge,
    validateWeight,
  } = usePetProfileValidation();

  const [petName, setPetName] = useState(petItem?.name);
  const [petAge, setPetAge] = useState<string>(String(petItem?.age));
  const [petWeight, setPetWeight] = useState<string>(String(petItem?.weight));
  const [petSize, setPetSize] = useState(petItem?.petSize);
  const [petImageUrl, setPetImageUrl] = useState(petItem?.imageUrl);

  const onClickRemoveButton = (petId: number) => {
    confirm('정말 삭제하시겠어요?') &&
      removePetMutation.removePet({ petId }).then(() => {
        const userInfo = JSON.parse(localStorage.getItem('userInfo')!);

        localStorage.setItem('userInfo', JSON.stringify({ ...userInfo, hasPet: false }));

        resetPetProfile();

        alert('반려동물 정보를 삭제했습니다.');
      });

    navigate(PATH.HOME);
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    validateName(e);
    setPetName(e.target.value);
  };

  const onChangeAge = (e: ChangeEvent<HTMLSelectElement>) => {
    if (validateAge(e)) setPetAge(e.target.value);
  };

  const onChangeWeight = (e: ChangeEvent<HTMLInputElement>) => {
    validateWeight(e);
    setPetWeight(e.target.value);
  };

  const onChangePetSize = (petSize: PetSize) => {
    setPetSize(petSize);
  };

  const onChangeImage = (imageUrl: string) => {
    setPetImageUrl(imageUrl);
  };

  const onSubmit = () => {
    if (petItem && isValidNameInput && isValidAgeSelect && isValidWeightInput) {
      const newPetProfile = {
        ...petItem,
        name: petName || petItem.name,
        age: Number(petAge) || petItem.age,
        weight: Number(petWeight) || petItem.weight,
        petSize: petSize || petItem.petSize,
        imageUrl: petImageUrl || petItem.imageUrl,
      };

      editPetMutation
        .editPet(newPetProfile)
        .then(() => {
          updatePetProfile(newPetProfile);
          resetPetItemQuery();
          alert('반려동물 정보 수정이 완료되었습니다.');
          navigate(PATH.HOME);
        })
        .catch(() => {
          alert('반려동물 정보 수정에 실패했습니다.');
          navigate(PATH.HOME);
        });

      return;
    }

    alert('올바른 정보를 입력해주세요!');
  };

  return (
    <div>
      {petItem && (
        <>
          <FormContainer>
            <PetInfoWrapper>
              <PetInfoInForm petItem={petItem} onChangeImage={onChangeImage} />
            </PetInfoWrapper>

            <div>
              <InputLabel htmlFor="pet-name">이름 입력</InputLabel>
              <Input
                id="pet-name"
                value={petName}
                type="text"
                required
                minLength={1}
                placeholder="여기를 눌러 아이의 이름을 입력해주세요."
                maxLength={10}
                isValid
                onChange={onChangeName}
                design="underline"
                fontSize="1.3rem"
              />

              <ErrorCaption>
                {isValidNameInput
                  ? ''
                  : '아이의 이름은 1~10글자 사이의 한글, 영어, 숫자만 입력 가능합니다.'}
              </ErrorCaption>
            </div>
            <div>
              <InputLabel htmlFor="pet-age">나이 선택</InputLabel>
              <PetAgeSelect id="pet-age" onChange={onChangeAge} initialAge={Number(petAge)} />
              <ErrorCaption>{isValidAgeSelect ? '' : '나이를 선택해주세요!'} </ErrorCaption>
            </div>
            <div>
              <InputLabel htmlFor="pet-weight">몸무게 입력</InputLabel>
              <WeightInputContainer>
                <Input
                  id="pet-weight"
                  type="text"
                  value={petWeight}
                  required
                  minLength={1}
                  placeholder="예) 7.5"
                  maxLength={5}
                  isValid
                  onChange={onChangeWeight}
                  design="underline"
                  fontSize="1.3rem"
                  inputMode="decimal"
                />
                <Kg>kg</Kg>
              </WeightInputContainer>
              <ErrorCaption>
                {isValidWeightInput
                  ? ''
                  : '몸무게는 0kg초과, 100kg이하 소수점 첫째짜리까지 입력이 가능합니다.'}
              </ErrorCaption>
            </div>
            {isMixedBreed(petItem.breed) && (
              <div>
                <InputLabel htmlFor="pet-size">크기 선택</InputLabel>
                <PetSizeContainer role="radiogroup" id="pet-size">
                  {PET_SIZES.map(size => (
                    <Label
                      key={size}
                      role="radio"
                      text={size}
                      onClick={() => onChangePetSize(size)}
                      clicked={petSize === size}
                    />
                  ))}
                </PetSizeContainer>
              </div>
            )}
          </FormContainer>

          <ButtonContainer>
            <BasicButton type="button" $isEditButton onClick={onSubmit}>
              <EditIconImage src={EditIconLight} alt="" />
              수정
            </BasicButton>
            <BasicButton
              type="button"
              $isEditButton={false}
              onClick={() => {
                onClickRemoveButton(petItem.id);
              }}
            >
              <img src={TrashCanIcon} alt="" />
              삭제
            </BasicButton>
          </ButtonContainer>
        </>
      )}
    </div>
  );
};

export default PetProfileEditionForm;

const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  padding: 2rem;
`;

const PetInfoWrapper = styled.div`
  margin-bottom: 2rem;
`;

const InputLabel = styled.label`
  display: block;

  margin-bottom: 0.4rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const ErrorCaption = styled.p`
  min-height: 1.7rem;
  margin-top: 1rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.warning};
  letter-spacing: -0.5px;
`;

const WeightInputContainer = styled.div`
  position: relative;
`;

const Kg = styled.p`
  position: absolute;
  top: 1.2rem;
  right: 1.2rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const PetSizeContainer = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.8rem;
  align-items: center;

  margin-top: 1.2rem;
`;

const ButtonContainer = styled.div`
  position: fixed;
  bottom: 4rem;
  left: 0;

  display: flex;
  gap: 1.6rem;

  width: 100%;
  padding: 0 2rem;
`;

const BasicButton = styled.button<{ $isEditButton: boolean }>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: ${({ $isEditButton }) => ($isEditButton ? '70%' : '30%')};
  height: 5.1rem;

  font-size: 1.6rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: 0.02rem;

  background-color: ${({ $isEditButton, theme }) =>
    $isEditButton ? theme.color.primary : '#E73846'};
  border: none;
  border-radius: 16px;

  transition: all 100ms ease-in-out;

  &:active {
    scale: 0.98;
  }

  &:disabled {
    cursor: not-allowed;

    background-color: ${({ theme }) => theme.color.grey300};
  }
`;

const EditIconImage = styled.img`
  margin-right: 0.4rem;
  margin-bottom: 0.2rem;
`;
