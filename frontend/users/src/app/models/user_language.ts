import { Language } from "./language";
import { LanguageLevel } from "./language_level";

export interface UserLanguage {
  languageId: number;
  languageLevelId: number;
  language?: Language;
  languageLevel?: LanguageLevel;
}
